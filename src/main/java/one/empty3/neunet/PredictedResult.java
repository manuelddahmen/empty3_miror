package one.empty3.neunet;

public class PredictedResult {
    private double outputValues;
    private double sigmaError;
    private String inputFilenameId;
    private String inputFileDescription;

    public double getOutputValues() {
        return outputValues;
    }

    public void setOutputValues(double outputValues) {
        this.outputValues = outputValues;
    }

    public double getSigmaError() {
        return sigmaError;
    }

    public void setSigmaError(double sigmaError) {
        this.sigmaError = sigmaError;
    }

    public String getInputFilenameId() {
        return inputFilenameId;
    }

    public void setInputFilenameId(String inputFilenameId) {
        this.inputFilenameId = inputFilenameId;
    }

    public String getInputFileDescription() {
        return inputFileDescription;
    }

    public void setInputFileDescription(String inputFileDescription) {
        this.inputFileDescription = inputFileDescription;
    }
}