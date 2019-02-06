package engine.net;

public interface ICompleteRunnable<T> {
    void onComplete(T result);
}
