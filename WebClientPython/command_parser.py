from query_date import QueryDate
from consultas import ConsultaHospedagem, ConsultaPassagem
from server_objects import Hospedagem, Passagem
import requests


class CommandParser:

    prev_consulta_hospedagem = None
    prev_consulta_passagem = None
    prev_consulta_pacote = None

    headers = {
        'content-type': 'application/json'
    }

    server_url = 'http://localhost:8084/WebApplication/webresources/Server/'

    def parse_command(self, command):
        if command.lower() == 'consulta h':
            self.handle_consulta_hospedagem(self.nova_consulta_hospedagem())

        elif command.lower() == 'consulta p':
            self.handle_consulta_passagem(self.nova_consulta_passagem())

        elif command.lower() == 'consulta pc':
            self.nova_consulta_pacote()

        elif command.lower() == 'compra h':
            self.nova_compra_hospedagem()

        elif command.lower() == 'compra p':
            self.nova_compra_passagem()

        elif command.lower() == 'compra pc':
            self.nova_compra_pacote()

        else:
            print('Comando nao suportado')

    def nova_compra_hospedagem(self):

        consulta_hospedagem = self.nova_consulta_hospedagem()

        price = input('Preco da hospedagem: ')
        consulta_hospedagem.price = price

        consulta_json = consulta_hospedagem.to_json_dict()

        # TODO Finish request.
        response = requests.post(
            self.server_url + 'compra/hospedagens',
            headers=self.headers,
            json=consulta_json
        )

        if not self.verify_response(response):
            return

        try:
            hospedagem = response.json()
        except ValueError:
            print('Nao foi possivel realizar a compra')
            return
    
        if not hospedagem:
            print('Nao foi possivel realizar a compra')

        print('Compra realizada com sucesso.')

    def nova_compra_passagem(self):

        consulta_passagem = self.nova_consulta_passagem()

        price = input('Preco da passagem: ')
        consulta_passagem.price = price

        consulta_json = consulta_passagem.to_json_dict()

        # TODO Finish request.
        response = requests.post(
            self.server_url + 'compra/passagens',
            headers=self.headers,
            json=consulta_json
        )

        if not self.verify_response(response):
            return

        try:
            passagem = response.json()
        except ValueError:
            print('Nao foi possivel realizar a compra')
            return

        if not passagem:
            print('Nao foi possivel realizar a compra')

        print('Compra realizada com sucesso.')

    def nova_compra_pacote(self):

        print('Detalhes da Hospedagem:')
        consulta_hospedagem = self.nova_consulta_hospedagem()

        price = input('Preco da hospedagem: ')
        consulta_hospedagem.price = price

        print('Detalhes da Passagem:')
        consulta_passagem = self.nova_consulta_passagem()

        price = input('Preco da passagem: ')
        consulta_passagem.price = price

        consulta_hospedagem_json = consulta_hospedagem.to_json_dict()
        consulta_passagem_json = consulta_passagem.to_json_dict()

        consulta_pacote = {
            'consultaPassagem': consulta_passagem_json,
            'consultaHospedagem': consulta_hospedagem_json
        }

        # TODO Finish request.
        response = requests.post(
            self.server_url + 'compra/pacotes',
            headers=self.headers,
            json=consulta_pacote
        )

        if not self.verify_response(response):
            return

        try:
            pacote = response.json()
        except ValueError:
            print('Nao foi possivel realizar a compra')
            return

        if not pacote:
            print('Nao foi possivel realizar a compra')

        print('Compra realizada com sucesso.')

    def handle_consulta_hospedagem(self, consulta_hospedagem):

        # TODO Finish request.
        response = requests.post(
            self.server_url + 'consulta/hospedagens',
            headers=self.headers,
            json=consulta_hospedagem.to_json_dict()
        )

        if not self.verify_response(response):
            return

        try:
            hospedagens = response.json()
        except ValueError:
            print('Nao foram encontradas hospedagens com os critérios estabelecidos.')
            return

        if not hospedagens:
            print('Nao foram encontradas hospedagens com os critérios estabelecidos.')

        print('Resultados encontrados:')

        for h in hospedagens:
            h = Hospedagem.from_server_json(h)
            entry_date = consulta_hospedagem.entry_date
            leave_date = consulta_hospedagem.leave_date
            min_spots = h.available_dates[entry_date]

            for date in range(entry_date + 1, leave_date + 1):
                spots_on_date = h.available_dates[date]
                if spots_on_date < min_spots:
                    min_spots = spots_on_date

            print('Preco da diaria: {}\nDisponiveis: {}\n'.format(h.price, min_spots))

    def nova_consulta_hospedagem(self):
        
        if self.prev_consulta_hospedagem is not None:
            repetir_consulta = input('Repetir ultima consulta (y/n)? ')
            if repetir_consulta.startswith('y'):
                return self.prev_consulta_hospedagem

        location = input('Local: ')
        entry_date = input('Dia de entrada (DD/MM/AAAA): ')
        leave_date = input('Dia de saída (DD/MM/AAAA): ')
        n_rooms = int(input('Número de quartos: '))
        n_people = int(input('Número de pessoas: '))
        
        entry_date = QueryDate(entry_date)
        leave_date = QueryDate(leave_date)

        consulta_hospedagem = ConsultaHospedagem(
            location, entry_date.repr_day, leave_date.repr_day, n_rooms, n_people
        )

        self.prev_consulta_hospedagem = consulta_hospedagem

        return consulta_hospedagem

    def handle_consulta_passagem(self, consulta_passagem):

        # TODO Finish request.        
        response = requests.post(
            self.server_url + 'consulta/passagens',
            headers=self.headers,
            json=consulta_passagem.to_json_dict()
        )

        if not self.verify_response(response):
            return

        try:
            passagens = response.json()
        except ValueError:
            print('Nao foram encontradas passagens com os critérios estabelecidos.')
            return

        if not passagens:
            print('Nao foram encontradas passagens com os critérios estabelecidos.')

        print('Resultados encontrados:')

        print('Passagens de ida:')

        for p in passagens['Ida']:
            p = Passagem.from_server_json(p)
            print('Preco da passagem: {}\nDisponiveis: {}\n'.format(p.price, p.n_spots_left))

        if not consulta_passagem.is_one_way:

            print('Passagens de volta:')

            for p in passagens['Volta']:
                p = Passagem.from_server_json(p)
                print('Preco da passagem: {}\nDisponiveis: {}\n'.format(p.price, p.n_spots_left))

    def nova_consulta_passagem(self):

        if self.prev_consulta_passagem is not None:
            repetir_consulta = input('Repetir ultima consulta (y/n)? ')
            if repetir_consulta.startswith('y'):
                return self.prev_consulta_passagem

        is_one_way = input('Passagem só de ida (y/n)? ').startswith('y')
        origin = input('Origem: ')
        destination = input('Destino: ')
        going_date = input('Data de ida (DD/MM/AAAA): ')
        return_date = ''

        if not is_one_way:
            return_date = input('Data de retorno (DD/MM/AAAA): ')

        n_people = int(input('Número de pessoas: '))

        going_date_obj = QueryDate(going_date)
        return_date_obj = None
        
        if not is_one_way:
            return_date_obj = QueryDate(return_date)

        if not is_one_way:
            consulta_passagem = ConsultaPassagem(
                is_one_way,
                origin, destination,
                going_date_obj.repr_day, return_date_obj.repr_day,
                n_people
            )
        else:
            consulta_passagem = ConsultaPassagem(
                is_one_way,
                origin, destination,
                going_date_obj.repr_day, 0,
                n_people
            )

        self.prev_consulta_passagem = consulta_passagem

        return consulta_passagem

    def nova_consulta_pacote(self):

        consulta_hospedagem = None
        consulta_passagem = None

        if self.prev_consulta_pacote is not None:
            repetir_consulta = input('Repetir ultima consulta (y/n)? ')
            if repetir_consulta.startswith("y"):
                consulta_hospedagem = self.prev_consulta_pacote.consulta_hospedagem
                consulta_passagem = self.prev_consulta_pacote.consulta_passagem
        
        if consulta_hospedagem is None and consulta_passagem is None:
            print('Detalhes da Hospedagem:')
            consulta_hospedagem = self.nova_consulta_hospedagem()

            print('Detalhes da Passagem: ')
            consulta_passagem = self.nova_consulta_passagem()

        if consulta_hospedagem is None or consulta_passagem is None:
            return

        
        consulta_hospedagem_json = consulta_hospedagem.to_json_dict()
        consulta_passagem_json = consulta_passagem.to_json_dict()

        consulta_pacote = {
            'consultaPassagem': consulta_passagem_json,
            'consultaHospedagem': consulta_hospedagem_json
        }       

        # TODO Finish request.
        response = requests.post(
            self.server_url + 'consulta/pacotes',
            headers=self.headers,
            json=consulta_pacote
        )

        if not self.verify_response(response):
            return

        try:
            response_json = response.json()
        except ValueError:
            print('Nao foram encontrados pacotes com os critérios estabelecidos.')
            return
    
        if not response_json:
            print('Nao foram encontrados pacotes com os critérios estabelecidos.')
        
        print('Hospedagens:')

        hospedagens = response_json['hospedagens']
        passagens = response_json['passagens']

        for h in hospedagens:
            h = Hospedagem.from_server_json(h)
            entry_date = consulta_hospedagem.entry_date
            leave_date = consulta_hospedagem.leave_date
            min_spots = h.available_dates[entry_date]

            for date in range(entry_date + 1, leave_date + 1):
                spots_on_date = h.available_dates[date]
                if spots_on_date < min_spots:
                    min_spots = spots_on_date

            print('Preco da diaria: {}\nDisponiveis: {}\n'.format(h.price, min_spots))

        print('------------------------------------------')

        print('Passagens de ida:')

        for p in passagens['Ida']:
            p = Passagem.from_server_json(p)
            print('Preco da passagem: {}\nDisponiveis: {}\n'.format(p.price, p.n_spots_left))

        if not consulta_passagem.is_one_way:

            print('Passagens de volta:')

            for p in passagens['Volta']:
                p = Passagem.from_server_json(p)
                print('Preco da passagem: {}\nDisponiveis: {}\n'.format(p.price, p.n_spots_left))

    def verify_response(self, response):

        if response.status_code != 200:
            print('Servidor nao respondeu OK')
            return False

        return True

