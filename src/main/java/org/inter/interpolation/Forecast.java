package org.inter.interpolation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Forecast {

    public Map<String, List<Integer>> forecast(Map<String, List<Integer>> data, int months) {

        for (Map.Entry<String, List<Integer>> entry : data.entrySet()) {
            List<Integer> values = entry.getValue();
            List<Integer> forecastedValues = new ArrayList<>();

            double[] coeff = linearRegression(values);

            for (int i = 0; i < months; i++) {
                int nextValue = (int) (coeff[0] * (values.size() + i + 1) + coeff[1]);
                forecastedValues.add(nextValue);
            }

            data.put(entry.getKey(), forecastedValues);
        }

        return data;
    }

    private double[] linearRegression(List<Integer> values) {
        int n = values.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += i + 1;
            sumY += values.get(i);
            sumXY += (i + 1) * values.get(i);
            sumX2 += (i + 1) * (i + 1);
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        return new double[]{slope, intercept};
    }
}
