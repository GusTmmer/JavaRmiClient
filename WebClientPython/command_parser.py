from query_date import QueryDate
from consultas import ConsultaHospedagem, ConsultaPassagem


class CommandParser:

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
    
    def nova_consulta_hospedagem(self):
        
        if self.prev_consulta_hospedagem is not None:
            repetir_consulta = input('Repetir ultima consulta (y/n)? ')
            if repetir_consulta.startswith('y'):
                return self.prev_consulta_hospedagem

        location = input("Local: ")
        entry_date = input("Dia de entrada (DD/MM/AAAA): ")
        leave_date = input("Dia de saída (DD/MM/AAAA): ")
        n_rooms = int(input("Número de quartos: "))
        n_people = int(input("Número de pessoas: "))
        
        entry_date = QueryDate(entry_date)
        leave_date = QueryDate(leave_date)

        consulta_hospedagem = ConsultaHospedagem(location, entry_date.repr_day, leave_date.repr_day, n_rooms, n_people)

        self.prev_consulta_hospedagem = consulta_hospedagem;

        return consulta_hospedagem;

    def nova_consulta_passagem(self):

        if self.prev_consulta_passagem is not None:
            repetir_consulta = input('Repetir ultima consulta (y/n)? ')
            if repetir_consulta.startswith('y'):
                return self.prev_consulta_passagem

        is_one_way = input("Passagem só de ida (y/n)? ").startswith('y')
        origin = input("Origem: ")
        destination = input("Destino: ")
        going_date = input("Data de ida (DD/MM/AAAA): ")
        return_date = ''

        if not is_one_way:
            return_date = input("Data de retorno (DD/MM/AAAA): ")

        n_people = int(input("Número de pessoas: "))

        going_date = QueryDate(going_date)
        return_date = None
        
        if not is_one_way:
            return_date = QueryDate(return_date)

        if not is_one_way:
            consulta_passagem = ConsultaPassagem(is_one_way, origin, destination, going_date.repr_day, return_date.repr_day, n_people)
        else:
            consulta_passagem = ConsultaPassagem(is_one_way, origin, destination, going_date.repr_day, 0, n_people)


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
            print("Detalhes da Hospedagem:")
            consulta_hospedagem = nova_consulta_hospedagem()

            print("Detalhes da Passagem: ")
            consulta_passagem = nova_consulta_passagem()

        if consulta_hospedagem is None or consulta_passagem is None:
            return
        
        response = requests.get('http://localhost:' + port + '/service/consulta/pacote',
                                headers=, json=
                   )

        if response.status_code != 200:
            print('Servidor nao respondeu OK')
            return

        try:
            response_json = response.json()
        except ValueError:
            print("Nenhum pacote encontrado com os criterios estabelecidos.")
            return
        
        print('Hospedagens:')

                
        System.out.println("Hospedagens:");

        for (Hospedagem h : pacote.getHospedagens()) {
            int entryDate = ch.getEntryDate();
            int leaveDate = ch.getLeaveDate();
            int minimumSpots = h.availableDates.get(entryDate);

            for (int date = entryDate + 1; date <= leaveDate; date++) {
                int spotsOnDate = h.availableDates.get(date);
                if (spotsOnDate < minimumSpots)
                    minimumSpots = spotsOnDate;
            }

            System.out.printf("Preco da diaria: %s\nDisponiveis: %d\n", h.getPrice(), minimumSpots);
        }

        System.out.println("------------------------------------------");

        System.out.println("Passagens de ida:");

        for (Passagem p : pacote.getPassagens().get("Ida")) {
            System.out.printf("Preco da passagem: %s\nDisponiveis: %d\n", p.getPrice(), p.getAvailableSpots());
        }

        if (!cp.isOneWay()) {

            System.out.println("Passagens de volta:");

            for (Passagem p : pacote.getPassagens().get("Volta")) {
                System.out.printf("Preco da passagem: %s\nDisponiveis: %d\n", p.getPrice(), p.getAvailableSpots());
            }
        }





