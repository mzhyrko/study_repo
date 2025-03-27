#include <stdexcept>
#include <string>

using namespace std;

class ArabRzymException : public exception {
private:
    string message;
public:
    ArabRzymException(const string& msg) : message(msg) {}
    const char* what() const noexcept override {
        return message.c_str();
    }
};
