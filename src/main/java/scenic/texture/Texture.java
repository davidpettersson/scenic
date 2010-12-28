package scenic.texture;

public class Texture {

    private final int id;
    private final int size;
    private final int res;

    Texture(int id, int size, int res) {
        this.id = id;
        this.size = size;
        this.res = res;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Texture other = (Texture) obj;
        if (this.res != other.res) {
            return false;
        }
        return true;
    }
}
