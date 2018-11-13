from command_parser import CommandParser


def main():

    print("Comandos disponíveis:\nConsultas:\n" + \
          "\tconsulta h (hospedagem)\n" + \
          "\tconsulta p (passagem)\n" + \
          "\tconsulta pc (pacote)\n" + \
          "Compras:\n" + \
          "\tcompra h (hospedagem)\n" + \
          "\tcompra p (passagem)\n" + \
          "\tcompra pc (pacote)\n" + \
          "Eventos:\n" + \
          "\tregistra evento\n" + \
          "\tremove evento\n"
    )

    parser = CommandParser()

    while True:

        client_command = input()
        parser.parse_command(client_command)



if __name__ == '__main__':
    main()
