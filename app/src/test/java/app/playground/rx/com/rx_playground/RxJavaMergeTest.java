package app.playground.rx.com.rx_playground;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public class RxJavaMergeTest {
    @Test
    public void basicObservableMerge() {
        List<Observable<Integer>> observables = new ArrayList<>();

        IntStream.range(0, 5)
                .forEach(i -> {
                    Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
                        @Override
                        public ObservableSource<? extends Integer> call() throws Exception {
                            return Observable.just(i);
                        }
                    });
                    observables.add(observable);
                });

        Observable.merge(observables)
                .subscribe(
                        result -> System.out.println("Result is " + result),
                        throwable -> System.out.println(throwable.getMessage())
                );
    }
}
