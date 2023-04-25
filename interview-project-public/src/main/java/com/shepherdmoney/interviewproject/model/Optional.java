import java.util.NoSuchElementException;
@RestController
public class Optional<T> {
    private final T value;

    private Optional() {
        this.value = null;
    }
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }
    public User getUser() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        if (!(value instanceof CreditCard)) {
            throw new NoSuchElementException("Value is not a CreditCard");
        }
        return ((CreditCard) value).getUser();
    }
    public int getId() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        if (!(value instanceof CreditCard)) {
            throw new NoSuchElementException("Value is not a CreditCard");
        }
        return ((CreditCard) value).getId();
    }
}