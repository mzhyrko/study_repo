#include <stdio.h>
#include <pthread.h>
#include <signal.h>
#include <unistd.h>

static int counter = 0;
static pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

int increase_counter_safe() {
    pthread_mutex_lock(&mutex);  

    int temp = counter;
    temp = temp + 1;
    counter = temp;

    for (volatile int i = 0; i < 100000000; i++);

    pthread_mutex_unlock(&mutex); 
    return counter;
}

void handler(int sig) {
    printf("Handler sygnału próbuje zwiększyć licznik...\n");
    increase_counter_safe();
    printf("Handler zakończony \n");
}

void* thread_func(void* arg) {
    printf("Watek zaczyna zwiekszanie...\n");
    increase_counter_safe();
    printf("Watek zakończył (oczekiwana wartosc: 1)\n");
    return NULL;
}

int main() {
    signal(SIGUSR1, handler);

    pthread_t t;
    pthread_create(&t, NULL, thread_func, NULL);

    sleep(1);  
    printf("Wysyłamy sygnał SIGUSR1...\n");
    raise(SIGUSR1); 

    pthread_join(t, NULL);

    printf("Wartość licznika: %d\n", counter);

    return 0;
}
