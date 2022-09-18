package one.empty3.neuralnetwork;

public class TrainDatasetImage {
    String label;
    double[] values;
    boolean train;
    String relativePath;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public boolean isTrain() {
        return train;
    }

    public void setTrain(boolean train) {
        this.train = train;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}
