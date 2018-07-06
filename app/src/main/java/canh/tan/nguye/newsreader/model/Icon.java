package canh.tan.nguye.newsreader.model;

public class Icon {
    private String url;
    private int width, height, bytes;
    private String format;
    private Object error;

    public Icon() {
    }

    public Icon(String url, int width, int height, int bytes, String format, Object error) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.bytes = bytes;
        this.format = format;
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
