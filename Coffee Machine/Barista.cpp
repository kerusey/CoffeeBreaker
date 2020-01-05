#include "Barista.h"

Barista::Barista(size_t _DrinkType,
				 bool _milk,
				 bool _chokolate,
				 bool _canella,
				 size_t _shugar) : DrinkType(_DrinkType), milk(_milk), chokolate(_chokolate), canella(_canella), sugar(_shugar) {}  

Barista::~Barista() {}
