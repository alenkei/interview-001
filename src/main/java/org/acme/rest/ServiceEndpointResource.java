package org.acme.rest;

import com.google.common.io.ByteStreams;
import org.acme.database.ImportLogEntity;
import org.acme.database.ReportDataEntity;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the rest service endpoint of the application
 */
@Path("/service")
public class ServiceEndpointResource {
    @Inject
    EntityManager em;

    // see application.properties
    @ConfigProperty(name = "file.upload.path")
    String uploadFolder;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("uploadReport")
    public String uploadReport(@MultipartForm ReportUploadRequest data) throws IOException {
        java.nio.file.Path directories = Files.createDirectories(Paths.get(uploadFolder));
        byte[] bytes = ByteStreams.toByteArray(data.getFile());
        String fileName = FilenameUtils.getName(data.getFileName());
        Files.write(directories.resolve(fileName), bytes);
        save(bytes, fileName);
        return "OK";
    }

    @Transactional
    public void save(byte[] bytes, String fileName) {
        ImportLogEntity importLogEntity = new ImportLogEntity().setFileName(fileName).setSize(bytes.length).setImportTime(LocalDateTime.now());

        String csv = new String(bytes, StandardCharsets.UTF_8);
        importLogEntity.setData(parseCsv(csv));
        em.persist(importLogEntity);
    }

    @Transactional
    public void delete(ImportLogEntity importLogEntity) {
        // TODO check if null

        // delete the entity
        importLogEntity.delete();
    }

    private List<ReportDataEntity> parseCsv(String reportCsv){
        List<ReportDataEntity> result = new ArrayList<>();
        String[] csvLines = reportCsv
                .replace("\"", "")
                .replace("$", "")
                .replace("€", "")
                .replace("¥", "")
                .replace("£", "")
                .split("\n");
        ReportDataEntity tmpEntity;
        for (int i=1; i<csvLines.length; i++) {
            String[] fields = csvLines[i].split(",");
            tmpEntity = new ReportDataEntity()
                    .setRecID(Long.parseLong(fields[0]))
                    .setFirstName(fields[1])
                    .setLastName(fields[2])
                    .setEmailAddress((fields[3]))
                    .setGender(fields[4])
                    .setUsdBalance(Double.parseDouble(fields[5]))
                    .setEurBalance(Double.parseDouble(fields[6]))
                    .setYenBalance(Double.parseDouble(fields[7]))
                    .setGbpBalance(Double.parseDouble(fields[8]));
            result.add(tmpEntity);
        }

        return result;
    }
}