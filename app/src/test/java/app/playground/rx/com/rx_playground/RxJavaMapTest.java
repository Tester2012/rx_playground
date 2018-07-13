package app.playground.rx.com.rx_playground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class RxJavaMapTest {
    @Test
    public void showDiffBetweenMapAndFlatMap() {
        // flatMap transforms an event to several others
        Observable.fromCallable(() -> 1)
                .flatMap(result -> Observable.just(1, 2, 3))
                .subscribe(result -> System.out.println("Result : " + result));

        // map transforms a return value of the event
        Observable.fromCallable(() -> 1)
                .map(result -> 2)//here a value is returned rather than an Observable
                .subscribe(result -> System.out.println("Result : " + result));
    }

    @Test
    public void fetchSeveralEventsAndTryFlatMap() {
        // flatMap is called 3 times providing 1, 3 and 5 in sequence
        Observable.just(1, 3, 5)
                .flatMap(result -> Observable.just(result))
                .subscribe(result -> System.out.println("Result : " + result));
    }

    @Test
    public void flatMapVsConcatMap() throws Exception {
        System.out.println("******** Using flatMap() *********");

        Observable.range(1, 15)
                .flatMap(item -> {
                    return Observable.just(item).delay(1, TimeUnit.MILLISECONDS);
                })
                .subscribe(x -> System.out.print(x + " "));

        Thread.sleep(100);

        System.out.println("\n******** Using concatMap() *********");

        Observable.range(1, 15)
                .concatMap(item -> Observable.just(item).delay(1, TimeUnit.MILLISECONDS))
                .subscribe(x -> System.out.print(x + " "));

        Thread.sleep(100);
    }
}
