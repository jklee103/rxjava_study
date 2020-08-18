import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*
import java.util.concurrent.TimeUnit

class Main {
}
fun main(){
//disposable -> ㅁㅔ모리 잡아먹는거 방지 . 인자를 줄수. 화면을 떠날때 하던 작업을 취소하도록, 다시 데이터 요청이 들어올때
    val compositeDisposable = CompositeDisposable()
    val disposable1 = Observable
        .just(1,2)
        //.map { it*2 } //saf 단일 추상함수를 이런식으로 쓸수 있
        .subscribe{println(it)}
    compositeDisposable.add(disposable1)
    compositeDisposable.clear() //dispose 보다는 clear 권장

    val disposable2 = Observable
        .just(3,4)
        .delay(1, TimeUnit.SECONDS) //set 하기전에 추가 방지
        //.map { it*2 } //saf 단일 추상함수를 이런식으로 쓸수 있
        .subscribe{println(it)}
    compositeDisposable.add(disposable2)
    Thread.sleep(2000L)

//    val eventSource = EventSource<String>()
////    eventSource.addObserver { println("첫번째 옵저버: $it") } /
////    eventSource.addObserver { println("두번째 옵저버: $it") }
//
//    eventSource.addObserver(object : Observer<String>{
//        override fun notify(event: String) {
//            println("첫번째 옵저버: $event")
//        }
//    })
//    eventSource.notify("hello")
//    eventSource.notify("observer pattern")

}

interface Observer<T>{ //typealias -> interface
    fun notify(event: T)
}

class EventSource<T>{
    private val observers = mutableListOf<Observer<T>>()
    fun addObserver(observer: Observer<T>){
        observers +=observer
    }

    fun notify(event: T) {
        observers.forEach { it.notify(event)}
    }

}