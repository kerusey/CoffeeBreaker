shitLines = open('shit.txt', 'r').readlines()
reqLines = open('requirements.txt', 'r').readlines()
requirements = [row for row in reqLines if row not in shitLines]
with open ('requirements.txt', 'w') as file:
	for item in requirements:
		file.write(str(item))
