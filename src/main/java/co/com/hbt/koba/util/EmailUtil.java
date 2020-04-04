package co.com.hbt.koba.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;


public class EmailUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);
	private static Session session = null;
	private static String emailID = null;
	static PropertiesLoader properties = PropertiesLoader.getInstance();

	public EmailUtil() {
		emailSetup();
		sendEmail(session, emailID, "SimpleEmail Testing Subject", "SimpleEmail Testing Body");
	}

	public static void sendImage(String pathImagen) {
		emailSetup();
		sendImageEmail(session, properties.getProperty("nc.correo.destino"), properties.getProperty("nc.correo.encabezado")
 				, properties.getProperty("nc.correo.mensaje"),pathImagen);
	}
	
	public static void sendArchivo(String origen, String destino, List<File> files) {
		emailSetup();
		sendAdjuntoEmail(session, properties.getProperty("nc.correo.destino"), properties.getProperty("nc.correo.encabezado")
 				, properties.getProperty("nc.correo.mensaje"),origen,destino,files);
	}

	private static void emailSetup() {
		emailID =  properties.getProperty("nc.correo.origen");
		Properties props = System.getProperties();
		props.put("mail.smtp.host", properties.getProperty("nc.correo.smtp"));
		props.put("mail.smtp.port", properties.getProperty("nc.correo.smtp.puerto"));
		Boolean isAutenticacion=Boolean.parseBoolean(properties.getProperty("nc.correo.esGmail"));
		if(isAutenticacion != null && isAutenticacion){
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.auth", "true");
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("nc.correo.origen"),  properties.getProperty("nc.correo.origen.pass"));
			}
		});
		}
		else{
			props.put("mail.smtp.starttls.enable", "true");
		}
	}

	private static void sendEmail(Session session, String toEmail, String subject, String body) {
		try {
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(properties.getProperty("nc.correo.origen"), "Test Automation (NoReply) - By HACC"));
			msg.setReplyTo(InternetAddress.parse(properties.getProperty("nc.correo.origen"), false));
			msg.setSubject(subject, "UTF-8");
			msg.setText(body, "UTF-8");
			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			LOGGER.info("Message is ready");
			Transport.send(msg);

			LOGGER.info("EMail Sent Successfully!!");
		} catch (Exception e) {
			LOGGER.error("Error sendEmail: ", e);
		}
	}

	private static void sendImageEmail(Session session, String toEmail, String subject, String body, String filename) {
		try {
			MimeMessage msg = new MimeMessage(session);
			DataSource srcImage = new FileDataSource(filename);
			
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(properties.getProperty("nc.correo.origen"), "Test Automation (NoReply) - By HACC"));
			msg.setReplyTo(InternetAddress.parse(properties.getProperty("nc.correo.origen"), false));
			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(body);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Second part is image attachment
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(srcImage));
			messageBodyPart.setFileName("evidence.png");
			// Trick is to add the content-id header here
			//messageBodyPart.setHeader("Content-ID", "image_id");
			messageBodyPart.setHeader("Content-ID", "<image>");
			multipart.addBodyPart(messageBodyPart);

			// third part for displaying image in the email body
			messageBodyPart = new MimeBodyPart();
			String htmlText = "<H1>Evidencia</H1><img src='cid:image'>";
	         messageBodyPart.setContent(htmlText, "text/html");
			multipart.addBodyPart(messageBodyPart);
			// Set the multipart message to the email message
			msg.setContent(multipart);
			
			// Send message
			Transport.send(msg);
			
			LOGGER.info("EMail Sent Successfully with image!!");
		} catch (Exception e) {
			LOGGER.error("Error sendEmailImage: ", e);
		}

	}
	
	
	public static void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parrentDirectoryName)
			throws Exception {
		if (fileToZip == null || !fileToZip.exists()) {
			return;
		}

		String zipEntryName = fileToZip.getName();
		if (parrentDirectoryName != null && !parrentDirectoryName.isEmpty()) {
			zipEntryName = parrentDirectoryName + "/" + fileToZip.getName();
		}

		if (fileToZip.isDirectory()) {
			for (File file : fileToZip.listFiles()) {
				addDirToZipArchive(zos, file, zipEntryName);
			}
		} else {
			byte[] buffer = new byte[1024];
			FileInputStream fis =null;
			try{
			fis=new FileInputStream(fileToZip);
			zos.putNextEntry(new ZipEntry(zipEntryName));
			int length;
			while ((length = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, length);
			}
			zos.closeEntry();
			}catch (Exception e) {
                // TODO: handle exception
            }
			finally {
                if(fis!=null){
                    fis.close();        
                }
            }
			
		}
	}

	private static void sendAdjuntoEmail(Session session, String toEmail, String subject, String body, String origen, String destino,List<File> files) {
		try {
			// Definicion del mensaje
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(properties.getProperty("nc.correo.origen")));
			message.setSubject(subject, "UTF-8");
			message.setSentDate(new Date());
			message.setRecipients(Message.RecipientType.TO,toEmail);
			// Se crea la parte para el contenido del mensaje y se rellena
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body, "text / html");
			// Se crea el objeto Multipart y se le agrega el contenido
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			//se elimina el zip anterior si exsite
			File archivoTemporal= new File(origen);
			if(archivoTemporal.exists()){
			    try{
			    Path path= Paths.get(archivoTemporal.getPath());
                cleanUp(path);
			    }
			    catch (Exception e) {
			        LOGGER.error("Se presento un error al eliminar el zip de cucumber reporting ", e);
                }
			    
			}
			//se genera el zip nuevo
			generarArchivoZip(origen,destino);
			File archivo= new File(origen);
			// Se leee y se adjunta el zip adjunta el archivo
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(archivo);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(archivo.getName());
			//TODO se comenta donde se agregar el cucumber reporting
			//multipart.addBodyPart(messageBodyPart);
			
			//se leen y adjuntan demas archivos
            if (files != null) {
                for (File archivoEncontrado : files) {
                    messageBodyPart = new MimeBodyPart();
                     source = new FileDataSource(archivoEncontrado);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(archivoEncontrado.getName());
                    multipart.addBodyPart(messageBodyPart);
                }

            }
			// Se incluye en el objeto Multipart y se envia
			message.setContent(multipart);
			message.saveChanges();
			Transport.send(message);

		} catch (Exception ex) {
			LOGGER.error("Error enviando el archivo adjunto: ", ex);
		}
	}

    private static void generarArchivoZip(String origen, String destino) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(origen);
            zos = new ZipOutputStream(fos);
            addDirToZipArchive(zos, new File(destino), null);
            zos.flush();
            fos.flush();
        } catch (Exception e) {
            LOGGER.error("Error comprimiendo el archivo: ", e);
        } finally {
            try {
                if(zos!= null){
                zos.close();
                }
                if(fos!=null){
                fos.close();
                }
            } catch (IOException e) {
                LOGGER.error("Error comprimiendo el archivo: ", e);
            }
            
        }
    }

    public static Reportable generarRepote() {
        try {
            String ruta = properties.getProperty("nc.reportes.ruta");
            File reportOutputDirectory = new File(ruta);
            List<String> jsonFiles = new ArrayList<>();
            jsonFiles.add(ruta + "/cucumber.json");
            String buildNumber = properties.getProperty("nc.reportes.compilacion");
            String projectName = properties.getProperty("nc.reportes.nombre");
            Configuration configuration = new Configuration(reportOutputDirectory, projectName);
            configuration.setBuildNumber(buildNumber);
            configuration.setSortingMethod(SortingMethod.NATURAL);
            configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
            configuration.setTrendsStatsFile(new File("target/test-classes/demo-trends.json"));

            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            return reportBuilder.generateReports();
        } catch (Exception e) {
            // se controla que si no genera reporte no se danie el proceso
            LOGGER.error("Se presento un error en la generación del reporte: ", e);
            return null;
        }
    }
    
    public static void cleanUp(Path path) throws NoSuchFileException, DirectoryNotEmptyException, IOException{
        Files.delete(path);
      }

}
