package app.playground.rx.com.rx_playground;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RxJavaMergeTest {
    @Test
    public void basicObservableMerge() {
        List<Observable<Integer>> observables = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            final int r = i;
            Observable<Integer> observable = Observable.defer(() -> Observable.just(r));
            observables.add(observable);
        }

        Observable.merge(observables)
                .subscribe(
                        result -> System.out.println("Result is " + result),
                        throwable -> System.out.println(throwable.getMessage())
                );
    }
}
