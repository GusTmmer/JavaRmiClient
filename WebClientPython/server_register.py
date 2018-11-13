
server_url = 'http://localhost:8084/webresources/Server/


while True:

    command = input()
    parse_command(command)



def parse_command(command: str):
    

    if command.lower() == 'add h':
        adiciona_hospedagem()

    elif command.lower() == 'add p':
        adiciona_passagem()

    else:
        print('Not supported')


def adiciona_hospedagem():

    location = input('Local: ')
    entry_date = input('Dia de entrada (DD/MM/AAAA): ')
    leave_date = input('Dia de saída (DD/MM/AAAA): ')
    n_rooms = int(input('Número de quartos: '))
    n_people = int(input('Número de pessoas: '))
    
    entry_date = QueryDate(entry_date)
    leave_date = QueryDate(leave_date)


def adiciona_passagem():


