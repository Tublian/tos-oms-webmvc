package com.oms.service;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.util.Arrays;

public class EmailFulfillmentServiceTest {

    private EmailService emailService;
    private EmailHttpClient mockEmailHttpClient;

    @Before
    public void setUp() {
        // Setup for Test 0: EmailService.sendEmail
        mockEmailHttpClient = Mockito.mock(EmailHttpClient.class);

        emailService = new EmailService();
        emailService.setSmtpHost("smtp.test.com");
        emailService.setSmtpPort(25);
        emailService.setEmailHttpClient(mockEmailHttpClient);
    }

    @Test
    public void testSendEmail_ValidEmailRequest() {
        // Arrange
        EmailResponse expectedResponse = new EmailResponse("SUCCESS");
        when(mockEmailHttpClient.sendEmail(any(EmailRequestDto.class), eq("smtp.test.com"), eq(25)))
            .thenReturn(expectedResponse);

        EmailRequestDto requestDto = new EmailRequestDto();
        requestDto.setSender("sender@test.com");
        requestDto.setRecipient("recipient@test.com");
        requestDto.setSubject("Test Subject");
        requestDto.setBody("Test Body");

        // Act
        EmailResponse actualResponse = emailService.sendEmail(requestDto);

        // Assert
        Assert.assertEquals("SUCCESS", actualResponse.getStatus());
        verify(mockEmailHttpClient, times(1))
            .sendEmail(eq(requestDto), eq("smtp.test.com"), eq(25));
    }

    @Test
    public void testModifyToShipping_ValidSalesOrder() {
        // Arrange: Create SalesOrder with order line and payment info
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setId(1L);

        OrderLine orderLine = new OrderLine();
        orderLine.setProduct("Test Product");
        orderLine.setQuantity(2);
        salesOrder.setOrderLines(Arrays.asList(orderLine));

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAmount(100.0);
        paymentInfo.setMethod("CreditCard");
        salesOrder.setPaymentInfo(paymentInfo);

        // Create mocks for dependencies for fulfillment service
        PaymentService mockPaymentService = Mockito.mock(PaymentService.class);
        DinersPaymentService mockDinersPaymentService = Mockito.mock(DinersPaymentService.class);
        SalesOrderRepository mockSalesOrderRepository = Mockito.mock(SalesOrderRepository.class);
        EmailService mockEmailService = Mockito.mock(EmailService.class);

        EmailResponse expectedEmailResponse = new EmailResponse("SUCCESS");
        when(mockEmailService.sendEmail(any(EmailRequestDto.class))).thenReturn(expectedEmailResponse);
        when(mockSalesOrderRepository.updateSalesOrder(any(SalesOrder.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        ModifyFulfillmentService fulfillmentService = new ModifyFulfillmentService();
        fulfillmentService.setPaymentService(mockPaymentService);
        fulfillmentService.setDinersPaymentService(mockDinersPaymentService);
        fulfillmentService.setSalesOrderRepository(mockSalesOrderRepository);
        fulfillmentService.setEmailService(mockEmailService);

        // Act: Invoke modifyToShipping which should process the order and trigger an email
        SalesOrder updatedOrder = fulfillmentService.modifyToShipping(salesOrder);

        // Assert: Verify that EmailService.sendEmail was called with the correct email content
        verify(mockEmailService, times(1)).sendEmail(argThat(new ArgumentMatcher<EmailRequestDto>() {
            @Override
            public boolean matches(EmailRequestDto emailRequest) {
                return emailRequest.getSubject() != null && emailRequest.getSubject().contains("Shipping");
            }
        }));
        // Assert that the order status was updated to "SHIPPING"
        Assert.assertEquals("SHIPPING", updatedOrder.getStatus());
    }
}

// Stub implementations for dependent classes and interfaces assumed to be present in the project

class EmailService {
    private String smtpHost;
    private int smtpPort;
    private EmailHttpClient emailHttpClient;

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public void setEmailHttpClient(EmailHttpClient emailHttpClient) {
        this.emailHttpClient = emailHttpClient;
    }

    public EmailResponse sendEmail(EmailRequestDto request) {
        return emailHttpClient.sendEmail(request, smtpHost, smtpPort);
    }
}

interface EmailHttpClient {
    EmailResponse sendEmail(EmailRequestDto request, String host, int port);
}

class EmailRequestDto {
    private String sender;
    private String recipient;
    private String subject;
    private String body;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

class EmailResponse {
    private String status;

    public EmailResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

class SalesOrder {
    private Long id;
    private String status;
    private java.util.List<OrderLine> orderLines;
    private PaymentInfo paymentInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.util.List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(java.util.List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}

class OrderLine {
    private String product;
    private int quantity;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class PaymentInfo {
    private double amount;
    private String method;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

interface PaymentService {
    // Payment processing logic
}

interface DinersPaymentService {
    // Diners payment logic
}

interface SalesOrderRepository {
    SalesOrder updateSalesOrder(SalesOrder order);
}

class ModifyFulfillmentService {
    private PaymentService paymentService;
    private DinersPaymentService dinersPaymentService;
    private SalesOrderRepository salesOrderRepository;
    private EmailService emailService;

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void setDinersPaymentService(DinersPaymentService dinersPaymentService) {
        this.dinersPaymentService = dinersPaymentService;
    }

    public void setSalesOrderRepository(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public SalesOrder modifyToShipping(SalesOrder order) {
        // Simulate payment authorization and order update logic
        // (Actual implementation would interact with paymentService and dinersPaymentService)

        // Update order status to SHIPPING
        order.setStatus("SHIPPING");

        // Create email request with proper email content for shipping notification
        EmailRequestDto emailRequest = new EmailRequestDto();
        emailRequest.setSender("no-reply@oms.com");
        emailRequest.setRecipient("customer@test.com");
        emailRequest.setSubject("Your order is now Shipping");
        emailRequest.setBody("Dear Customer, Your order is on its way.");

        // Invoke EmailService to send email notification
        emailService.sendEmail(emailRequest);

        // Update order using repository and return the updated SalesOrder
        return salesOrderRepository.updateSalesOrder(order);
    }
}