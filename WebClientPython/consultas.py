import json


class ConsultaHospedagem:

    def __init__(self,
                 location: str,
                 entry_date: int, leave_date: int,
                 n_rooms: int, n_people: int):

        self.location = location
        self.entry_date = entry_date
        self.leave_date = leave_date
        self.n_rooms = n_rooms
        self.n_people = n_people
        self.price = ''

    def to_json_dict(self):

        json_dict = {
            'location': self.location,
            'entryDate': self.entry_date,
            'leaveDate': self.leave_date,
            'nRooms': self.n_rooms,
            'nPeople': self.n_people,
            'price': self.price
        }

        return json_dict


class ConsultaPassagem:

    def __init__(self,
                 is_one_way: bool,
                 origin: str, destination: str,
                 going_date: int, return_date: int,
                 n_people: int):

        self.is_one_way = is_one_way
        self.origin = origin
        self.destination = destination
        self.going_date = going_date
        self.return_date = return_date
        self.n_people = n_people
        self.price = ''

    def to_json_dict(self):

        json_dict = {
            'isOneWay': self.is_one_way,
            'origin': self.origin,
            'destination': self.destination,
            'goingDate': self.going_date,
            'returnDate': self.return_date,
            'nPeople': self.n_people,
            'price': self.price
        }

        return json_dict
    



