/*
	barista lib

	input parameters from app called somehow
*/

#pragma once
#include <iostream>

#define espresso 1
#define kapuchino 2
#define latte 3
#define latte_makiato 4
#define cafe_crema 5  // FIXMYNAME
#define americano 6 

/*  also custom stuff like tseh 95 does  */

class Barista {
	
	size_t DrinkType;
	bool milk;
	bool chokolate;
	bool canella;
	size_t shugar; // 1-3 cubes of shugar 

public:

	Barista (size_t DrinkType = espresso,
			bool milk = false,
			bool chokolate = false,
			bool canella = false,
			size_t shugar = 0); // WRITEME

	~Barista();
};