package com.joaquin;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * P = Monto del préstamo
 * i = Tasa de interés mensual
 * n = Plazo del crédito en meses
 * <p>
 * Cuota mensual = (P * i) / (1 - (1 + i) ^ (-n))
 */
public class JavaLambda implements RequestHandler<BankRequest, BankResponse> {


    @Override
    public BankResponse handleRequest(BankRequest bankRequest, Context context) {

        context.getLogger().log("Bank Request: " + bankRequest);

        MathContext mathContext = MathContext.DECIMAL128;

        BigDecimal amount = bankRequest.getAmount().setScale(2, RoundingMode.HALF_UP); //10.4 Redondeo del banquero
        BigDecimal monthyRate = bankRequest.getRate()
                .setScale(2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), mathContext);

        BigDecimal monthyRateWithAccount = bankRequest.getRate()
                .subtract(BigDecimal.valueOf(0.2), mathContext)
                .setScale(2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), mathContext);

        Integer term = bankRequest.getTerm();

        BigDecimal monthlyPayment = this.caculateQuota(amount, monthyRate, term, mathContext);
        BigDecimal monthlyPaymentWithAccount = this.caculateQuota(amount, monthyRateWithAccount, term, mathContext);

        BankResponse bankResponse = new BankResponse();
        bankResponse.setQuota(monthlyPayment);
        bankResponse.setRate(monthyRate);
        bankResponse.setTerm(term);
        bankResponse.setQuotaWithAccount(monthlyPaymentWithAccount);
        bankResponse.setRateWithAccount(monthyRateWithAccount);
        bankResponse.setTermWithAccount(term);

        return bankResponse;
    }

    /**
     * P = Monto del préstamo
     * i = Tasa de interés mensual
     * n = Plazo del crédito en meses
     * <p>
     * Cuota mensual = (P * i) / (1 - (1 + i) ^ (-n))
     */
    public BigDecimal caculateQuota(BigDecimal amount, BigDecimal rate, Integer term, MathContext mathContext) {
        //Calcular el (1+i)
        BigDecimal onePlusRate = rate.add(BigDecimal.ONE, mathContext);
        //potencia a la (1 + i) ^ (-n) y luego tomar el reciproco para obtener ((1 + i) ^ (-n))
        BigDecimal onePlusRateToN = onePlusRate.pow(term, mathContext); //Reciproco de un numero
        BigDecimal onePlusRateToNegativeN = BigDecimal.ONE.divide(onePlusRateToN, mathContext);

        //Calcular couta mensual
        BigDecimal numerator = amount.multiply(rate, mathContext);
        BigDecimal denominator = BigDecimal.ONE.subtract(onePlusRateToNegativeN, mathContext);

        BigDecimal monthlyPayment = numerator.divide(denominator, mathContext);

        //Redondear a 2 decimales
        monthlyPayment = monthlyPayment.setScale(2, RoundingMode.HALF_UP);
        return monthlyPayment;
    }

}