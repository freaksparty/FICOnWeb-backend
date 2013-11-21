package es.ficonlan.web.backend.util.exceptions;

@SuppressWarnings("serial")
public class DuplicatedInstanceException extends InstanceException {

    public DuplicatedInstanceException(Object key, String className) {
        super("Duplicate instance", key, className);
    }
    
}
