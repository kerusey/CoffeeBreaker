from django.db import models

class CoffeeBreakerDataTable(models.Model):
    coffeeID = models.CharField(db_column='coffeeID', max_length=40)
    id = models.CharField(db_column='id', max_length=40, primary_key=True)
    water = models.CharField(db_column='water', max_length=40)
    milk = models.CharField(db_column='milk', max_length=40)
    sugar = models.CharField(db_column='sugar', max_length=40)
    date = models.CharField(db_column='date', max_length=40)

    def __str__(self):
    	return [self.id, self.coffeeID, self.water, self.milk, self.sugar, self.date]