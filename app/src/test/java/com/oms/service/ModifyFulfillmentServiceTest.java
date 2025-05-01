
@@
Line 62: 
-        Assert.assertNotNull(modifiedOrder);
+        Assert.assertNotNull(modifiedOrder);
+        Mockito.verify(emailService).sendEmail(Mockito.any());
@@
Line 83:
-        Assert.assertEquals(authorizedB4 - returnAuth.getAmount(), authorizedAf, 0.0);
+        Assert.assertEquals(authorizedB4 - returnAuth.getAmount(), authorizedAf, 0.0);
+        Mockito.verify(emailService).sendEmail(Mockito.any());
