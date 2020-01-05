#include <iostream>
#include <string>
#include <random>
#include <algorithm>
#include <iterator>

using namespace std;

string const default_chars = "abcdefghijklmnaoqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

string random_string (size_t len = 25, string const &allowed_chars = default_chars) {
	mt19937_64 gen { random_device()() };
	uniform_int_distribution<size_t> dist { 0, allowed_chars.length()-1 };
	string ret;
	generate_n(back_inserter(ret), len, [&] { return allowed_chars[dist(gen)]; });
	return ret;
} // OK

string const Suffix = ":%-_-%:"; // SETME FIXME
string const AppLink = "https://coffeebreaker.com"; // SETME FIXME !
string const UnicID = "1"; // SETME FIXME
string const UnicKey  = random_string(); // OK 
string QRLink = AppLink + Suffix + UnicID + Suffix + UnicKey;

int main() {
   cout << QRLink << endl; /* Works fine */
}