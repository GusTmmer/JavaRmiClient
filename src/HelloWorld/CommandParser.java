package HelloWorld;

import Consultas.ConsultaHospedagem;
import Consultas.ConsultaPacoteResponse;
import Consultas.ConsultaPassagem;
import Consultas.Date;
import Supervisionados.Hospedagem;
import Supervisionados.Pacote;
import Supervisionados.Passagem;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandParser {

    private Scanner scanner;
    private InterfaceServ server;

    CommandParser(InterfaceServ server, Scanner scanner) {
        this.scanner = scanner;
        this.server = server;
    }


    public void parseCommand(String command) {

        if (command.equalsIgnoreCase("consulta h")) {
            handleConsultaHospedagem(novaConsultaHospedagem());

        } else if (command.equalsIgnoreCase("consulta p")) {
            handleConsultaPassagem(novaConsultaPassagem());

        } else if (command.equalsIgnoreCase("consulta pc")) {
            novaConsultaPacote();

        } else if (command.equalsIgnoreCase("compra h")) {
            novaCompraHospedagem();

        } else if (command.equalsIgnoreCase("compra p")) {
            novaCompraPassagem();

        } else if (command.equalsIgnoreCase("compra pc")) {
            novaCompraPacote();

        } else if (command.equalsIgnoreCase("register event")) {
            novoRegistroDeEvento();

        } else {
            System.out.println("Not supported.");
        }
    }

    private void novoRegistroDeEvento() {

    }

    private void novaCompraPacote() {

    }

    private void novaConsultaPacote() {

        System.out.println("Detalhes da Hospedagem:");
        ConsultaHospedagem ch = novaConsultaHospedagem();

        System.out.println("Detalhes da Passagem: ");
        ConsultaPassagem cp = novaConsultaPassagem();

        try {
            ConsultaPacoteResponse pacote = server.consultaPacote(cp, ch);

            if (pacote == null) {
                System.out.println("Nenhum pacote encontrado com os criterios estabelecidos.");
                return;
            }

            System.out.println("Hospedagens:");
            for (Hospedagem h : pacote.getHospedagens()) {
                System.out.printf("Preço da hospedagem: %s\n", h.getPrice());
            }

            System.out.println("------------------------------------------");

            System.out.println("Passagens de ida:");

            for (Passagem p : pacote.getPassagens().get("Ida")) {
                System.out.printf("Preco da passagem: %s\n", p.getPrice());
            }

            if (!cp.isOneWay()) {

                System.out.println("Passagens de volta:");

                for (Passagem p : pacote.getPassagens().get("Volta")) {
                    System.out.printf("Preco da passagem: %s\n", p.getPrice());
                }
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void novaCompraPassagem() {

    }

    private void novaCompraHospedagem() {

    }

    private ConsultaHospedagem novaConsultaHospedagem() {

        System.out.print("Local: ");
        String location = scanner.nextLine();

        System.out.print("Dia de entrada (DD/MM/AAAA): ");
        String entryDate = scanner.nextLine();

        System.out.print("Dia de saída (DD/MM/AAAA): ");
        String leaveDate = scanner.nextLine();

        System.out.print("Número de quartos: ");
        int nRooms = Integer.parseInt(scanner.nextLine());

        System.out.print("Número de pessoas: ");
        int nPeople = Integer.parseInt(scanner.nextLine());

        Date entryDateObj = new Date(entryDate);
        Date leaveDateObj = new Date(leaveDate);

        ConsultaHospedagem consultaHospedagem = new ConsultaHospedagem (
                location, entryDateObj.reprDay, leaveDateObj.reprDay, nRooms, nPeople
        );

        return consultaHospedagem;
    }

    private void handleConsultaHospedagem(ConsultaHospedagem consultaHospedagem) {

        try {
            List<Hospedagem> availableLodgings = server.consultaHospedagem(consultaHospedagem);

            if (availableLodgings == null) {
                System.out.println("Nao foram encontradas hospedagens com os critérios estabelecidos.");
                return;
            }

            for (Hospedagem h : availableLodgings) {
                System.out.printf("Preco da diaria: %s\n", h.getPrice());
            }

        } catch (RemoteException ex) {
            Logger.getLogger(CommandParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ConsultaPassagem novaConsultaPassagem() {

        System.out.println("Passagem só de ida (y/n)?  ");
        boolean isOneWay = scanner.nextLine().startsWith("y");

        System.out.print("Origem: ");
        String origin = scanner.nextLine();

        System.out.print("Destino: ");
        String destination = scanner.nextLine();

        System.out.print("Data de ida (DD/MM/AAAA): ");
        String goingDate = scanner.nextLine();

        String returnDate = "";
        if (!isOneWay) {
            System.out.print("Data de retorno (DD/MM/AAAA): ");
            returnDate = scanner.nextLine();
        }

        System.out.print("Número de pessoas: ");
        String nPeople = scanner.nextLine();


        Date goingDateObj = new Date(goingDate);
        Date returnDateObj = new Date(returnDate);


        ConsultaPassagem consultaPassagem = new ConsultaPassagem(
                isOneWay,
                origin,
                destination,
                goingDateObj.reprDay,
                returnDateObj.reprDay,
                Integer.parseInt(nPeople)
        );

        return consultaPassagem;
    }

    private void handleConsultaPassagem(ConsultaPassagem consultaPassagem) {

        try {

            Map<String, List<Passagem>> availableTickets = server.consultaPassagem(consultaPassagem);

            if (availableTickets == null) {
                System.out.println("Nao foram encontradas passagens com os critérios estabelecidos.");
                return;
            }

            System.out.println("Passagens de ida:");

            for (Passagem p : availableTickets.get("Ida")) {
                System.out.printf("Preco da passagem: %s\n", p.getPrice());
            }

            if (!consultaPassagem.isOneWay()) {

                System.out.println("Passagens de volta:");

                for (Passagem p : availableTickets.get("Volta")) {
                    System.out.printf("Preco da passagem: %s\n", p.getPrice());
                }
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
