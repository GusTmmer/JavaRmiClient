

class QueryDate:

    days_in_a_month = (31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    def __init__(self, date_str: str):

        date_parts = date_str.split('/')
        
        int_date_parts = [int(dp) for dp in date_parts]

        self.day, self.month, self.year = int_date_parts

        self.repr_day = self.day + self.year * 365

        for m in range(self.month):
            self.repr_day += days_in_a_month[m]

    
    def __init__(self, day: int, month: int, year: int):

        self.day = day
        self.month = month
        self.year = year

        self.repr_day = self.day + self.year * 365

        for m in range(self.month):
            self.repr_day += days_in_a_month[m]

        

        

        


