package com.csye6220.Elearning.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/payment")

//Controller to handle payments with stripe api
public class PaymentController1 {

    @Value("${stripe.key.secret}")
    private String stripeSecretKey;

    @PostMapping("/createe-charge")
    public String createCharge(
            @RequestParam String cardNumber,
            @RequestParam String expMonth,
            @RequestParam String expYear,
            @RequestParam String cvc,
            @RequestParam String email,
            @RequestParam int amount) {

        try {
            Stripe.apiKey = stripeSecretKey;

            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount); // Amount in cents (USD 9.99)
            params.put("currency", "usd");
            params.put("description", "Leather Bag");
            params.put("source", createToken(cardNumber, expMonth, expYear, cvc));
            params.put("receipt_email", email);

            Charge charge = Charge.create(params);

// Handle the success or redirect to a success page
            return "success-page";
        } catch (StripeException e) {
            e.printStackTrace();
// Handle the failure or redirect to an error page
            return "error-page";
        }
    }



    //helper method to create Token from the inputs

    private String createToken(String cardNumber, String expMonth, String expYear, String cvc) throws StripeException {
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", cardNumber);
        cardParams.put("exp_month", expMonth);
        cardParams.put("exp_year", expYear);
        cardParams.put("cvc", cvc);

        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);

        return com.stripe.model.Token.create(tokenParams).getId();
    }
}