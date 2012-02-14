package com.mondora.chargify.controller;

import com.mondora.chargify.domain.*;
import com.mondora.chargify.exception.*;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/24/11 5:08 PM
 */
public class Chargify {
    static String contentType = "application/xml";
    static String charset = "UTF-8";
    protected static Logger logger = LoggerFactory.getLogger(Chargify.class);
    private static final String AUTO_GENERATED = "@auto generated@";

    private BeanFactory beanFactory;
    protected HttpHost host;
    CredentialsProvider credsProvider;

    String domain = "emptyDomain";      // the domain
    String key = "empty";

    public Chargify(String key, String domain) {
        setup(key, domain);
    }

    protected void setup(String key, String domain) {
        this.domain = domain;
        this.key = key;
        host = new HttpHost(domain + ".chargify.com", 443, "https");


        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(key, "x");
        credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(domain + ".chargify.com", 443, "ChargifyService API"), creds);

        // todo preemtive and cache autentication
//        client.getParams().setAuthenticationPreemptive(true);
//        org.apache.http.client.AuthCache authCache = new BasicAuthCache();
//        // Generate BASIC scheme object and add it to the local auth cache
//        BasicScheme basicAuth = new BasicScheme();
//        authCache.put("chargify.com", basicAuth);

        if (logger.isDebugEnabled())
            logger.debug("ChargifyService client created for domain " + domain);
    }

    public String getDomain() {
        return domain.toLowerCase();
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public MeteredUsage componentCreateUsage(Subscription sub, Component comp, Integer quantity, String memo) throws ChargifyException {
        return componentCreateUsage(String.valueOf(sub.id), String.valueOf(comp.getId()), quantity, memo);
    }

    public MeteredUsage componentCreateUsage(String sub, String comp, Integer quantity, String memo) throws ChargifyException {
        String xml =
                "<usage>" +
                        "<id>" + AUTO_GENERATED + "</id>" +
                        "<quantity type=\"integer\">" + Integer.toString(quantity) + "</quantity>" +
                        "<memo>" + String.valueOf(memo) + "</memo>" +
                        "</usage>";

        HttpPost method = new HttpPost("/subscriptions/" + sub + "/components/" + comp + "/usages.xml");
        try {
            StringEntity entity = new StringEntity(xml);
            entity.setContentEncoding(charset);
            entity.setContentType(contentType);
            method.setEntity(entity);

            HttpResponse response = executeHttpMethod(method);
            handleResponseCode(response, method);
            return (MeteredUsage) parse(MeteredUsage.class, response, method);
        } catch (Exception e) {
            throw new ChargifyException(e);
        }
    }

    public Customer getCustomerByReference(String reference) throws ChargifyException {
        HttpGet method = new HttpGet("/customers/lookup.xml?reference=" + reference);
        try {
            HttpResponse response = executeHttpMethod(method);
            handleResponseCode(response, method);
            return (Customer) parse(Customer.class, response, method);
        } catch (NotFoundException nfe) {
            return null;
        } catch (Exception e) {
            throw new ChargifyException(e);
        }
    }

    public Subscriptions listSubscriptions(Customer customer) throws ChargifyException {
        HttpGet method = new HttpGet("/customers/" + customer.id + "/subscriptions.xml");
        try {
            HttpResponse response = executeHttpMethod(method);
            handleResponseCode(response, method);
            return (Subscriptions) parse(Subscriptions.class, response, method);
        } catch (NotFoundException nfe) {
            return null;
        } catch (Exception e) {
            throw new ChargifyException(e);
        }
    }

    public Subscription getSubscription(Integer id) throws ChargifyException {
        HttpGet method = new HttpGet("/subscriptions/" + String.valueOf(id) + ".xml");
        try {
            HttpResponse response = executeHttpMethod(method);
            handleResponseCode(response, method);
            return (Subscription) parse(Subscription.class, response, method);
        } catch (NotFoundException nfe) {
            return null;
        } catch (Exception e) {
            throw new ChargifyException(e);
        }
    }

    public String deleteSubscription(Integer id, String reason) throws ChargifyException {
        HttpDelete method = new HttpDelete("/subscriptions/" + id + ".xml");
        try {
            /*
            // HttpClient does not support request entities with the DELETE operation
            String xml = "<subscription>" +
                         "<cancellation_message>"+reason+"</cancellation_message>" +
                         "</subscription>";
                method.setRequestEntity( new StringRequestEntity( xml, contentType, charset));
             */
            return executeHttp(method);
        } catch (NotFoundException nfe) {
            return null;
        } catch (Exception e) {
            throw new ChargifyException(e);
        }
    }

    public Product getProduct(String id) throws ChargifyException {
        HttpGet method = new HttpGet("/products/" + id + ".xml");
        try {
            HttpResponse response = executeHttpMethod(method);
            handleResponseCode(response, method);
            return (Product) parse(Product.class, response, method);
        } catch (NotFoundException nfe) {
            if (logger.isDebugEnabled()) {
                logger.debug("Product " + id + " Not Found. ", nfe);
            } else {
                logger.info("Product " + id + " " + nfe.getMessage());
            }
            return null;
        } catch (Exception e) {
            throw new ChargifyException(e);
        }
    }

    public Product getProductByHandle(String handle) throws ChargifyException {
        return getProduct("handle/" + handle);
    }

    public Products listProducts() throws ChargifyException {
        HttpGet method = new HttpGet("/products.xml");
        try {
            HttpResponse response = executeHttpMethod(method);
            handleResponseCode(response, method);
            return (Products) parse(Products.class, response, method);
        } catch (NotFoundException nfe) {
            return null;
        } catch (Exception e) {
            throw new ChargifyException(e);
        }
    }

    public Components listComponents(Subscription subscription) throws ChargifyException {
        HttpGet method = new HttpGet("/subscriptions/" + subscription.id + "/components.xml");
        try {
            HttpResponse response = executeHttpMethod(method);
            handleResponseCode(response, method);
            return (Components) parse(Components.class, response, method);
        } catch (NotFoundException nfe) {
            return null;
        } catch (Exception e) {
            throw new ChargifyException(e);
        }
    }


    public Components listComponents(String productFamily) throws ChargifyException {
        HttpGet method = new HttpGet("/product_families/" + productFamily + "/components.xml");
        try {
            HttpResponse response = executeHttpMethod(method);
            handleResponseCode(response, method);
            return (Components) parse(Components.class, response, method);
        } catch (NotFoundException nfe) {
            return null;
        } catch (Exception e) {
            throw new ChargifyException(e);
        }
    }

    Object parse(Class clazz, HttpResponse response, HttpRequestBase method) throws ChargifyException {
        if (response.getEntity() == null) {
            if (logger.isTraceEnabled())
                logger.trace("parse: entity is null " + method.getMethod() + " " + method.getURI().toString() + "\n" + method);
            return null;
        }
        try {
            if (logger.isTraceEnabled())
                logger.trace("parse: " + method.getMethod() + " " + method.getURI().toString() + "\n" + method);
            return parse(clazz, response.getEntity().getContent());
        } catch (IOException e) {
            if (logger.isTraceEnabled())
                logger.trace("parse: " + e.getMessage(), e);
            if (logger.isInfoEnabled())
                logger.info("parse: " + e.getMessage());
            return null;
        }
    }

    protected Object parse(Class clazz, InputStream inputStream) throws ChargifyException {
        byte[] in = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(null);
            if (logger.isTraceEnabled()) {
                try {
                    in = readByteStream(inputStream);
                    logger.trace("Response input " + new String(in));

                    inputStream = new ByteArrayInputStream(in);
                } catch (IOException e) {
                }
            }
            Object xmlObject = clazz.cast(unmarshaller.unmarshal(inputStream));
            return xmlObject;
        } catch (JAXBException e) {
            if (logger.isTraceEnabled())
                logger.trace(e.getMessage(), e);
            if (logger.isInfoEnabled())
                logger.info(e.getMessage());
            throw new ChargifyException("Unparsable Entity " + new String(in));
        }
    }

    protected String toXml(Object o) {
        try {
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, charset);
            marshaller.setSchema(null);
            marshaller.marshal(o, sw);
            return sw.toString();
        } catch (JAXBException e) {
            if (logger.isTraceEnabled())
                logger.trace(e.getMessage(), e);
            if (logger.isInfoEnabled())
                logger.info(e.getMessage());
        }
        return null;
    }

    protected String handleResponseCode(HttpResponse response, HttpRequestBase method) throws ChargifyException {
        StatusLine line = response.getStatusLine();
        String errorMsg = method.getMethod() + " " + String.valueOf(method.getURI()) + " Error " + String.valueOf(line.getStatusCode()) + " " + line.getReasonPhrase();
        int code = line.getStatusCode();
        if (isError(code)) {
            try {
                Errors error = (Errors) parse(Errors.class, response, method);
                if (error != null) {
                    errorMsg = String.valueOf(error) + ". Method " + errorMsg;
                }
            } catch (Exception ex) {
                errorMsg += " " + ex.getMessage() + " " + ex.getStackTrace()[0];
            }
        }
        return handleResponse(code, errorMsg);
    }

    /**
     * @param code, the http response code
     * @return true if the code is > 400
     */
    protected boolean isError(int code) {
        return code > 400;
    }

    protected String handleResponse(int statusCode, String errorMsg) throws AuthenticationFailedException, DisabledEndpointException, NotFoundException, InternalServerException, InvalidRequestException {
        switch (statusCode) {
            case 401:
                throw new AuthenticationFailedException(errorMsg);
            case 403:
                throw new DisabledEndpointException(errorMsg);
            case 404:
                throw new NotFoundException(errorMsg);
            case 500:
                throw new InternalServerException(errorMsg);
            case 422: {
                if (errorMsg.contains("must be unique - that value has been taken")) {
                    throw new DuplicateEntityException(errorMsg);
                }
                throw new InvalidRequestException(errorMsg);
            }
        }
        return String.valueOf(statusCode);
    }

    protected String executeHttp(HttpRequestBase method) throws ChargifyException, IOException {
        if (logger.isDebugEnabled()) logger.debug(method.getMethod() + " " + method.getURI().toString());
        HttpResponse response = executeHttpMethod(method);
        return handleResponseCode(response, method);
    }

    protected HttpResponse executeHttpMethod(HttpRequestBase method) throws IOException {
        if (logger.isDebugEnabled()) logger.debug(method.getMethod() + " " + method.getURI().toString());
        return getClient().execute(host, method);
    }

    protected HttpClient getClient() {
        DefaultHttpClient httpClient = createHttpClient();
        httpClient.setCredentialsProvider(credsProvider);
        return httpClient;
    }

    protected DefaultHttpClient createHttpClient() {
        if (beanFactory == null) {
            beanFactory = new XmlBeanFactory(new ClassPathResource("sense-context.xml"));
        }
        return (DefaultHttpClient) beanFactory.getBean("httpClient", DefaultHttpClient.class);
    }

    public Customer create(Customer customer) throws ChargifyException {
        String xml = toXml(customer);
        HttpPost method = new HttpPost("/customers.xml");
        try {
            StringEntity entity = new StringEntity(xml);
            entity.setContentEncoding(charset);
            entity.setContentType(contentType);
            method.setEntity(entity);
            if (logger.isTraceEnabled()) {
                logger.trace("createCustomer " + xml);
            }
            HttpResponse response = executeHttpMethod(method);
            handleResponseCode(response, method);
            return (Customer) parse(Customer.class, response, method);
        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (ChargifyException ce) {
            throw ce;
        } catch (Exception e) {
            throw new ChargifyException(e);
        }
    }

    public Subscription createSubscription(String productHandle, Integer customerId) throws ChargifyException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<subscription>" +
                "<product_handle>" + productHandle + "</product_handle>" +
                "<customer_id>" + customerId.toString() + "</customer_id>" +
                "</subscription>";
        return createSubscription(xml);
    }

    public Subscription create(Subscription subscription) throws ChargifyException {
        String xml = toXml(subscription);
        return createSubscription(xml);
    }

    protected Subscription createSubscription(String xml) throws ChargifyException {
        HttpPost method = new HttpPost("/subscriptions.xml");
        try {
            StringEntity entity = new StringEntity(xml);
            entity.setContentEncoding(charset);
            entity.setContentType(contentType);
            method.setEntity(entity);
            if (logger.isTraceEnabled()) {
                logger.trace("createSubscription " + xml);
            }

            HttpResponse response = executeHttpMethod(method);
            handleResponseCode(response, method);
            return (Subscription) parse(Subscription.class, response, method);
        } catch (NotFoundException nfe) {
            return null;
        } catch (ChargifyException ce) {
            throw ce;
        } catch (Exception e) {
            throw new ChargifyException(e);
        }
    }

//    protected Component createComponent(Component c, ProductFamily pf) throws ChargifyException {
//        HttpPost method = new HttpPost("/product_families/" + pf.id + "/metered_components.xml");
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
//                "<metered_component>" +
//                "<name>" + c.name + "</name>" +
//                "<unit_name>" + c.unitName + "</unit_name>" +
//                "<pricing_scheme>per unit</pricing_scheme>" +
//                "<unit_price>"+c.unitBalance+"</unit_price>" +
//                "</metered_component>";
//        try {
//            StringEntity entity = new StringEntity(xml);
//            entity.setContentEncoding(charset);
//            entity.setContentType(contentType);
//            method.setEntity(entity);
//            if (logger.isTraceEnabled()) {
//                logger.trace("createComponent " + xml);
//            }
//
//            HttpResponse response = executeHttpMethod(method);
//            handleResponseCode(response, method);
//            return (Component) parse(Component.class, response, method);
//        } catch (NotFoundException nfe) {
//            return null;
//        } catch (ChargifyException ce) {
//            throw ce;
//        } catch (Exception e) {
//            throw new ChargifyException(e);
//        }
//    }

    public Customer buildACustomer(String reference, String organization, String firstName, String lastName, String email) throws ChargifyException {
        validateParam(reference, "Reference");
        validateParam(email, "Email");
        Customer nuovo = new Customer();
        nuovo.reference = reference;
        nuovo.firstName = firstName.trim();
        nuovo.lastName = lastName.trim();
        nuovo.organization = organization.trim();
        nuovo.email = email.trim();
        return nuovo;
    }

    private void validateParam(String reference, String field) throws ChargifyException {
        if (reference == null || (reference != null && "".equals(reference.trim()))) {
            throw new ChargifyException("Cannot create a customer with empty or null " + field);
        }
    }

    /**
     * Read a bytearray from an inputstream. Iterate on {@see FTP_BUFFER } buffer size for reading.
     *
     * @param stream
     * @return {link byte[]} all the incoming stream
     * @throws IOException
     */
    public static byte[] readByteStream(InputStream stream) throws IOException {
        if (stream != null) {
            byte[] b = new byte[FTP_BUFFER];
            int read = stream.read(b);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            while (read > -1) {
                bos.write(b, 0, read);
                read = stream.read(b);
            }
            b = bos.toByteArray();
            if (logger.isDebugEnabled()) {
                String debug;
                if (b.length > 100)
                    debug = new String(b).substring(0, 100) + "[...]";
                else
                    debug = new String(b);
                logger.debug("readByteStream:" + debug);
            }
            stream.close();
            return b;
        }
        return null;
    }

    static final int FTP_BUFFER = 1024;


}