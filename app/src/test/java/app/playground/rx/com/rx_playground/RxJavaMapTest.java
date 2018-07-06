package app.playground.rx.com.rx_playground;

import org.junit.Test;

import io.reactivex.Observable;

public class RxJavaMapTest {
    @Test
    public void showDiffBetweenMapAndFlatMap() {
        // flatMap transforms an event to several others
        Observable.fromCallable(() -> 1)
                .flatMap(result -> Observable.just(1, 2, 3))
                .subscribe(result -> System.out.println("Result : " + result));

        // map transforms an event to another and that's it
        Observable.fromCallable(() -> 1)
                .map(result -> 2)
                .subscribe(result -> System.out.println("Result : " + result));
    }
}
