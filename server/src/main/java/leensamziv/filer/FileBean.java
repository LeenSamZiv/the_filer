package leensamziv.filer;

public class FileBean {
    String name;
    long lastModified;
    long size;

    public FileBean() {
    }

    public FileBean(String name, long lastModified, long size) {
        this.name = name;
        this.lastModified = lastModified;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
