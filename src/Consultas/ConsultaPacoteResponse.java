package Consultas;

import Supervisionados.Hospedagem;
import Supervisionados.Passagem;

import java.util.List;
import java.util.Map;

public class ConsultaPacoteResponse {

    private List<Hospedagem> hospedagens;
    private Map<String, List<Passagem>> passagens;

    public ConsultaPacoteResponse(List<Hospedagem> hospedagens, Map<String, List<Passagem>>passagens) {
        this.hospedagens = hospedagens;
        this.passagens = passagens;
    }

    public List<Hospedagem> getHospedagens() {
        return hospedagens;
    }

    public Map<String, List<Passagem>> getPassagens() {
        return passagens;
    }
}
