#include <iostream>
#include <thread>
#include <mutex>
#include <string>
#include <chrono>

std::mutex mtx;

void countUp() {
    for (int i = 1; i <= 20; ++i) {
        std::this_thread::sleep_for(std::chrono::milliseconds(100)); 
        std::lock_guard<std::mutex> lock(mtx);
        std::cout << "Thread 1 counting up: " << i << std::endl;
    }
}
void countDown() {
    for (int i = 20; i >= 0; --i) {
        std::this_thread::sleep_for(std::chrono::milliseconds(100));
        std::lock_guard<std::mutex> lock(mtx);
        std::cout << "Thread 2 counting down: " << i << std::endl;
    }
}
int main() {
    std::thread thread1(countUp);

    // Wait for thread to finish
    thread1.join();

    std::thread thread2(countDown);

    thread2.join();

    return 0;
}
