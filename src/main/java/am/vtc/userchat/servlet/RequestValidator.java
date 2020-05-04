package am.vtc.userchat.servlet;

public class RequestValidator<T> {
    private T entity;
    private boolean isValid;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
