package org.acme.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import org.acme.database.ImportLogEntity;
import org.acme.database.ReportDataEntity;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This is the rest service endpoint of the application
 */
@Path("/service")
public class ServiceEndpointResource {
    // see application.properties
    @ConfigProperty(name = "file.upload.path")
    String uploadFolder;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/records/{recID}")
    public Response getRecord(@PathParam Long recID) {
        String json = "[";

        try {
            Optional<ReportDataEntity> record = ReportDataEntity.findByRecID(recID);

            if (record.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            ObjectMapper mapper = new ObjectMapper();
            json += mapper.writeValueAsString(record.get());
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        json += "]";

        return Response.ok(json).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/records")
    public Response getAll() {
        String json = "[";

        try {
            Optional<List<ReportDataEntity>> records = ReportDataEntity.getAll();

            if (records.isEmpty() || records.get().isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            ObjectMapper mapper = new ObjectMapper();
            var first = true;
            for (var record: records.get()) {
                if (!first) {
                    json += ",";
                } else {
                    first = false;
                }
                json += mapper.writeValueAsString(record);
            }
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        json += "]";

        return Response.ok(json).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/uploadReport")
    public Response uploadReport(@MultipartForm ReportUploadRequest data) throws IOException {
        Long id;
        try {
            java.nio.file.Path directories = Files.createDirectories(Paths.get(uploadFolder));
            byte[] bytes = ByteStreams.toByteArray(data.getFile());
            String fileName = FilenameUtils.getName(data.getFileName());
            Files.write(directories.resolve(fileName), bytes);
            id = save(bytes, fileName);
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(id.toString()).build();
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/deleteReport/{reportID}")
    public Response deleteReport(@PathParam Long reportID) {
        try {
            if (!delete(reportID)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok("Deleted!").build();
    }

    @Transactional
    public Long save(byte[] bytes, String fileName) {
        ImportLogEntity importLogEntity = new ImportLogEntity().setFileName(fileName).setSize(bytes.length).setImportTime(LocalDateTime.now());

        String csv = new String(bytes, StandardCharsets.UTF_8);
        importLogEntity.setData(parseCsv(csv));
        importLogEntity.persist();
        return importLogEntity.id;
    }

    @Transactional
    public boolean delete(Long id) {
        ImportLogEntity importLogEntity = ImportLogEntity.findById(id);

        if (null != importLogEntity) {
            importLogEntity.delete();
            return true;
        }

        return false;
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
            tmpEntity.persist();
            result.add(tmpEntity);
        }

        return result;
    }
}