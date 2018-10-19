package HelloWorld;

import ClientEvents.HospedagemEvent;
import ClientEvents.PacoteEvent;
import ClientEvents.PassagemEvent;
import Consultas.*;
import Supervisionados.Hospedagem;
import Supervisionados.Passagem;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *  Responsible for processing command inputted by the client.
 *  Sends various requests to the server.
 */
public class CommandParser {

    private Scanner scanner;
    private InterfaceServ server;
    private CliImpl client;

    private ConsultaPassagem prevConsultaPassagem = null;
    private ConsultaHospedagem prevConsultaHospedagem = null;
    private ConsultaPacote prevConsultaPacote = null;

    private class ConsultaPacote {
        ConsultaPassagem consultaPassagem;
        ConsultaHospedagem consultaHospedagem;

        public ConsultaPacote(ConsultaPassagem consultaPassagem, ConsultaHospedagem consultaHospedagem) {
            this.consultaPassagem = consultaPassagem;
            this.consultaHospedagem = consultaHospedagem;
        }
    }

    CommandParser(CliImpl client, InterfaceServ server, Scanner scanner) {
        this.server = server;
        this.client = client;
        this.scanner = scanner;
    }


    /** Parses commands inputted by the user, getting appropriate handler.
     *
     * @param command : A string containing the command inputted by the user.
     */
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

        } else if (command.equalsIgnoreCase("registra evento")) {
            novoRegistroDeEvento();
        } else if (command.equalsIgnoreCase("remove evento")) {
            removeRegistroDeEvento();

        } else {
            System.out.println("Not supported.");
        }
    }

    /**
     * Specifies a handler to build the appropriate event removal request.
     */
    private void removeRegistroDeEvento() {

        System.out.println("Tipo de evento:\n1. Hospedagem.\n2. Passagem.\n3. Pacote.");
        Integer eventOption = Integer.parseInt(scanner.nextLine());

        if (eventOption == 1) {
            removeEventoHospedagem();
        } else if (eventOption == 2) {
            removeEventoPassagem();
        } else if (eventOption == 3) {
            removeEventoPacote();
        } else {
            System.out.println("Opcao invalida.");
        }
    }

    /**
     * Builds a structure specifying the details of the event.
     * Then removes it at the server side.
     */
    private void removeEventoPacote() {

        System.out.println("Detalhes da Passagem: ");

        System.out.print("Origem: ");
        String origin = scanner.nextLine();

        System.out.print("Destino: ");
        String destination = scanner.nextLine();

        System.out.print("Data (DD/MM/AAAA): ");
        String date = scanner.nextLine();

        System.out.print("Número de pessoas: ");
        int nPeople = Integer.parseInt(scanner.nextLine());

        System.out.print("Preço máximo da passagem: ");
        float price = Float.parseFloat(scanner.nextLine());

        PassagemEvent passagemEvent = new PassagemEvent(
                origin,
                destination,
                date,
                nPeople,
                price
        );

        System.out.println("Detalhes da Hospedagem:");

        System.out.print("Local: ");
        String location = scanner.nextLine();

        System.out.print("Dia de entrada (DD/MM/AAAA): ");
        String entryDate = scanner.nextLine();

        System.out.print("Dia de saída (DD/MM/AAAA): ");
        String leaveDate = scanner.nextLine();

        System.out.print("Número de quartos: ");
        int nRooms = Integer.parseInt(scanner.nextLine());

        System.out.print("Preço máximo da hospedagem: ");
        price = Float.parseFloat(scanner.nextLine());

        HospedagemEvent hospedagemEvent = new HospedagemEvent(
                location,
                entryDate,
                leaveDate,
                nRooms,
                price
        );

        PacoteEvent pacoteEvent = new PacoteEvent(hospedagemEvent, passagemEvent);

        try {
            server.removeInteresse(client, pacoteEvent);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("Evento removido.");
    }

    /**
     * Builds a structure specifying the details of the event.
     * Then removes it at the server side.
     */
    private void removeEventoPassagem() {

        System.out.println("Detalhes da Passagem: ");

        System.out.print("Origem: ");
        String origin = scanner.nextLine();

        System.out.print("Destino: ");
        String destination = scanner.nextLine();

        System.out.print("Data (DD/MM/AAAA): ");
        String date = scanner.nextLine();

        System.out.print("Número de pessoas: ");
        int nPeople = Integer.parseInt(scanner.nextLine());

        System.out.print("Preço máximo da passagem: ");
        float price = Float.parseFloat(scanner.nextLine());

        PassagemEvent passagemEvent = new PassagemEvent(
                origin,
                destination,
                date,
                nPeople,
                price
        );

        try {
            server.removeInteresse(client, passagemEvent);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("Evento removido.");
    }

    /**
     * Builds a structure specifying the details of the event.
     * Then removes it at the server side.
     */
    private void removeEventoHospedagem() {

        System.out.println("Detalhes da Hospedagem:");

        System.out.print("Local: ");
        String location = scanner.nextLine();

        System.out.print("Dia de entrada (DD/MM/AAAA): ");
        String entryDate = scanner.nextLine();

        System.out.print("Dia de saída (DD/MM/AAAA): ");
        String leaveDate = scanner.nextLine();

        System.out.print("Número de quartos: ");
        int nRooms = Integer.parseInt(scanner.nextLine());

        System.out.print("Preço máximo da hospedagem: ");
        float price = Float.parseFloat(scanner.nextLine());

        HospedagemEvent hospedagemEvent = new HospedagemEvent(
                location,
                entryDate,
                leaveDate,
                nRooms,
                price
        );

        try {
            server.removeInteresse(client, hospedagemEvent);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("Evento removido.");
    }

    /**
     * Specifies a handler to build the appropriate event.
     */
    private void novoRegistroDeEvento() {

        System.out.println("Tipo de evento:\n1. Hospedagem.\n2. Passagem.\n3. Pacote.");
        Integer eventOption = Integer.parseInt(scanner.nextLine());

        if (eventOption == 1) {
            registroEventoHospedagem();
        } else if (eventOption == 2) {
            registroEventoPassagem();
        } else if (eventOption == 3) {
            registroEventoPacote();
        } else {
            System.out.println("Opcao invalida.");
        }
    }

    /**
     * Builds a structure specifying the details of the event.
     * Then registers it at the server side.
     */
    private void registroEventoPacote() {

        System.out.println("Detalhes da Passagem: ");

        System.out.print("Origem: ");
        String origin = scanner.nextLine();

        System.out.print("Destino: ");
        String destination = scanner.nextLine();

        System.out.print("Data (DD/MM/AAAA): ");
        String date = scanner.nextLine();

        System.out.print("Número de pessoas: ");
        int nPeople = Integer.parseInt(scanner.nextLine());

        System.out.print("Preço máximo da passagem: ");
        float price = Float.parseFloat(scanner.nextLine());

        PassagemEvent passagemEvent = new PassagemEvent(
                origin,
                destination,
                date,
                nPeople,
                price
        );

        System.out.println("Detalhes da Hospedagem:");

        System.out.print("Local: ");
        String location = scanner.nextLine();

        System.out.print("Dia de entrada (DD/MM/AAAA): ");
        String entryDate = scanner.nextLine();

        System.out.print("Dia de saída (DD/MM/AAAA): ");
        String leaveDate = scanner.nextLine();

        System.out.print("Número de quartos: ");
        int nRooms = Integer.parseInt(scanner.nextLine());

        System.out.print("Preço máximo da hospedagem: ");
        price = Float.parseFloat(scanner.nextLine());

        HospedagemEvent hospedagemEvent = new HospedagemEvent(
                location,
                entryDate,
                leaveDate,
                nRooms,
                price
        );

        PacoteEvent pacoteEvent = new PacoteEvent(hospedagemEvent, passagemEvent);

        try {
            server.registraInteresse(client, pacoteEvent);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("Evento registrado.");
    }

    /**
     * Builds a structure specifying the details of the event.
     * Then registers it at the server side.
     */
    private void registroEventoPassagem() {

        System.out.println("Detalhes da Passagem: ");

        System.out.print("Origem: ");
        String origin = scanner.nextLine();

        System.out.print("Destino: ");
        String destination = scanner.nextLine();

        System.out.print("Data (DD/MM/AAAA): ");
        String date = scanner.nextLine();

        System.out.print("Número de pessoas: ");
        int nPeople = Integer.parseInt(scanner.nextLine());

        System.out.print("Preço da máximo da passagem: ");
        float price = Float.parseFloat(scanner.nextLine());

        PassagemEvent passagemEvent = new PassagemEvent(
                origin,
                destination,
                date,
                nPeople,
                price
        );

        try {
            server.registraInteresse(client, passagemEvent);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("Evento registrado.");
    }

    /**
     * Builds a structure specifying the details of the event.
     * Then registers it at the server side.
     */
    private void registroEventoHospedagem() {

        System.out.println("Detalhes da Hospedagem:");

        System.out.print("Local: ");
        String location = scanner.nextLine();

        System.out.print("Dia de entrada (DD/MM/AAAA): ");
        String entryDate = scanner.nextLine();

        System.out.print("Dia de saída (DD/MM/AAAA): ");
        String leaveDate = scanner.nextLine();

        System.out.print("Número de quartos: ");
        int nRooms = Integer.parseInt(scanner.nextLine());

        System.out.print("Preço máximo da hospedagem: ");
        float price = Float.parseFloat(scanner.nextLine());

        HospedagemEvent hospedagemEvent = new HospedagemEvent(
                location,
                entryDate,
                leaveDate,
                nRooms,
                price
        );

        try {
            server.registraInteresse(client, hospedagemEvent);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("Evento registrado.");
    }

    /**
     * Builds a structure specifying the details of the package.
     * Makes a requisition to the server to buy the product.
     */
    private void novaCompraPacote() {

        System.out.println("Detalhes da Hospedagem:");
        ConsultaHospedagem ch = novaConsultaHospedagem();

        System.out.print("Preço da hospedagem: ");
        String price = scanner.nextLine();

        ch.setPrice(price);

        System.out.println("Detalhes da Passagem: ");
        ConsultaPassagem cp = novaConsultaPassagem();

        System.out.print("Preço da passagem: ");
        price = scanner.nextLine();

        cp.setPrice(price);

        try {
            CompraPacoteResponse pacote = server.compraPacote(cp, ch);

            if (pacote == null) {
                System.out.println("Nao foi possivel realizar a compra.");
                return;
            }

            System.out.println("Compra realizada com sucesso.");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds a structure specifying the details of the package.
     * Makes a requisition to the server and gets a query response.
     */
    private void novaConsultaPacote() {

        ConsultaHospedagem ch = null;
        ConsultaPassagem cp = null;

        if (prevConsultaPacote != null) {
            System.out.print("Repetir ultima consulta (y/n)? ");
            if (scanner.nextLine().startsWith("y")) {
                ch = prevConsultaPacote.consultaHospedagem;
                cp = prevConsultaPacote.consultaPassagem;
            }
        }

        if (ch == null && cp == null) {
            System.out.println("Detalhes da Hospedagem:");
            ch = novaConsultaHospedagem();

            System.out.println("Detalhes da Passagem: ");
            cp = novaConsultaPassagem();
        }

        if (ch == null || cp == null)
            return;

        try {
            ConsultaPacoteResponse pacote = server.consultaPacote(cp, ch);

            if (pacote == null) {
                System.out.println("Nenhum pacote encontrado com os criterios estabelecidos.");
                return;
            }

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

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds a structure specifying the details of the plane ticket.
     * Makes a requisition to the server to buy the product.
     */
    private void novaCompraPassagem() {

        ConsultaPassagem consultaPassagem = novaConsultaPassagem();

        System.out.print("Preço da passagem: ");
        String price = scanner.nextLine();

        consultaPassagem.setPrice(price);

        try {

            Map<String, Passagem> tickets = server.compraPassagem(consultaPassagem);

            if (tickets == null) {
                System.out.println("Nao foi possivel realizar a compra.");
                return;
            }

            System.out.println("Compra realizada com sucesso.");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds a structure specifying the details of the lodging.
     * Makes a requisition to the server to buy the product.
     */
    private void novaCompraHospedagem() {

        ConsultaHospedagem consultaHospedagem = novaConsultaHospedagem();

        System.out.print("Preço da hospedagem: ");
        String price = scanner.nextLine();

        consultaHospedagem.setPrice(price);

        try {
            Hospedagem hospedagem = server.compraHospedagem(consultaHospedagem);

            if (hospedagem == null) {
                System.out.println("Nao foi possivel realizar a compra.");
                return;
            }

            System.out.println("Compra realizada com sucesso.");

        } catch (RemoteException ex) {
            Logger.getLogger(CommandParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Builds a structure specifying the details of the lodging.
     * Makes a requisition to the server and gets a query response.
     */
    private ConsultaHospedagem novaConsultaHospedagem() {

        if (prevConsultaHospedagem != null) {
            System.out.print("Repetir ultima consulta (y/n)? ");
            if (scanner.nextLine().startsWith("y")) {
                return prevConsultaHospedagem;
            }
        }

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

        prevConsultaHospedagem = consultaHospedagem;

        return consultaHospedagem;
    }

    /**
     * Processes the lodging query result generated by the server.
     * Outputs details to the terminal.
     */
    private void handleConsultaHospedagem(ConsultaHospedagem consultaHospedagem) {

        try {
            List<Hospedagem> availableLodgings = server.consultaHospedagem(consultaHospedagem);

            if (availableLodgings == null) {
                System.out.println("Nao foram encontradas hospedagens com os critérios estabelecidos.");
                return;
            }

            System.out.println("Resultados encontrados:");

            for (Hospedagem h : availableLodgings) {
                int entryDate = consultaHospedagem.getEntryDate();
                int leaveDate = consultaHospedagem.getLeaveDate();
                int minimumSpots = h.availableDates.get(entryDate);

                for (int date = entryDate + 1; date <= leaveDate; date++) {
                    int spotsOnDate = h.availableDates.get(date);
                    if (spotsOnDate < minimumSpots)
                        minimumSpots = spotsOnDate;
                }

                System.out.printf("Preco da diaria: %s\nDisponiveis: %d\n", h.getPrice(), minimumSpots);
            }



        } catch (RemoteException ex) {
            Logger.getLogger(CommandParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Builds a structure specifying the details of the plane ticket.
     * Makes a requisition to the server and gets a query response.
     */
    private ConsultaPassagem novaConsultaPassagem() {

        if (prevConsultaPassagem != null) {
            System.out.print("Repetir ultima consulta (y/n)? ");
            if (scanner.nextLine().startsWith("y")) {
                return prevConsultaPassagem;
            }
        }

        System.out.print("Passagem só de ida (y/n)? ");
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
        Date returnDateObj = null;
        
        if (!isOneWay)
            returnDateObj = new Date(returnDate);

        ConsultaPassagem consultaPassagem;

        if (!isOneWay) {
            consultaPassagem = new ConsultaPassagem(
                    false,
                    origin,
                    destination,
                    goingDateObj.reprDay,
                    returnDateObj.reprDay,
                    Integer.parseInt(nPeople)
            );
        } else {
            consultaPassagem = new ConsultaPassagem(
                    true,
                    origin,
                    destination,
                    goingDateObj.reprDay,
                    0,
                    Integer.parseInt(nPeople)
            );
        }

        prevConsultaPassagem = consultaPassagem;

        return consultaPassagem;
    }

    /**
     * Processes the plane tickets query result generated by the server.
     * Outputs details to the terminal.
     */
    private void handleConsultaPassagem(ConsultaPassagem consultaPassagem) {

        try {

            Map<String, List<Passagem>> availableTickets = server.consultaPassagem(consultaPassagem);

            if (availableTickets == null) {
                System.out.println("Nao foram encontradas passagens com os critérios estabelecidos.");
                return;
            }

            System.out.println("Passagens encontradas:");
            System.out.println("Passagens de ida:");

            for (Passagem p : availableTickets.get("Ida")) {
                System.out.printf("Preco da passagem: %s\nDisponiveis: %d\n", p.getPrice(), p.getAvailableSpots());
            }

            if (!consultaPassagem.isOneWay()) {

                System.out.println("Passagens de volta:");

                for (Passagem p : availableTickets.get("Volta")) {
                    System.out.printf("Preco da passagem: %s\nDisponiveis: %d\n", p.getPrice(), p.getAvailableSpots());
                }
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
