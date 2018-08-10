package app.playground.rx.com.rx_playground;

import org.junit.Test;
import org.robolectric.shadows.ShadowLooper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxJavaConcatTest {
	@Test
	public void testRetryObservableConcat() throws Exception {
		final List<Observable<String>> observables = new CopyOnWriteArrayList<>(getObservables());
		Observable.concat(observables)
			//remove the observable if it's successful
			.doOnNext(result -> {
				observables.remove(0);
			})
			.doOnError(error -> System.out.println(error.getMessage()))
			//if an error is thrown then retry the task only for this Observable
			.retry(10, error -> true)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(result -> System.out.println(result),
				error -> System.out.println("onError called!"));

		Thread.sleep(1000);

		ShadowLooper.runUiThreadTasks();
	}

	private List<Observable<String>> getObservables() {
		List<Observable<String>> observables = new ArrayList<>();
		final Random random = new Random();
		final AtomicInteger atomicInteger = new AtomicInteger(1);
		for (int i = 0; i < 3; i++) {
			final int id = i;
			observables.add(Observable.fromCallable(() -> {
				//for second Observable there's a 2/3 probability that it won't succeed
				if (id == 1 && random.nextInt() % 3 != 0) {
					throw new RuntimeException("Observable #" + id + " failed!");
				}
				return "Observable #" + id + " returns " + atomicInteger.getAndIncrement();
			}));
		}

		return observables;
	}
}
