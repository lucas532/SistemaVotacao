window.onload = function () {
    definirEstadoVotacao();
};

function clock() {
    const dataAtual = new Date();
    const clockElement = document.getElementById('clock');
    clockElement.textContent = dataAtual.toLocaleString();

    const horaAtual = dataAtual.toLocaleTimeString('pt-BR');
    const inicioVotacao = new Date(2021, 12, 12, 18, 0, 0).toLocaleTimeString('pt-BR');
    const fimVotacao = new Date(2021, 12, 12, 11, 0, 0).toLocaleTimeString('pt-BR');

    if (horaAtual === inicioVotacao) {
        abrirVotacao();
    }
    if (horaAtual === fimVotacao) {
        encerrarVotacao();
    }
}

setInterval(clock, 1000);

function votar() {
    delete Array.prototype.toJSON //Necessário remover pois entra em conflito com JSON.stringfy
    const myJson = {
        pessoa: {
            nomePessoa: $('#nomeFuncionario').val()
        },
        restaurante: {
            nomeRestaurante: $('#nomeRestaurante').val()
        }
    };

    fetch('http://localhost:8080/votos', {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        method: 'post',
        body: JSON.stringify(myJson)
    }).then(response => response.json())
        .then(json => alert(json.mensagem))
        .catch(err => alert(err));

    $("input[type=text]").val("");
    $("#textoVazio").addClass('HIDDEN');
    $('#listaResult').addClass('HIDDEN');
}

buscarVotos = async () => {
    const response = await fetch('http://localhost:8080/votos');
    const myJson = await response.json();
    if (Object.keys(myJson).length === 0) {
        $('#listaResult').addClass('HIDDEN');
        $("#textoVazio").removeClass('HIDDEN');
    } else {
        $("#textoVazio").addClass('HIDDEN');
        const lR = $('#listaResult');
        lR.empty();
        lR.removeClass('HIDDEN');
        lR.append(document.createElement('strong').textContent = 'RESULTADO');
        for (const voto of myJson) {
            let listItem = document.createElement('li');
            listItem.appendChild(
                document.createElement('strong')
            ).textContent = voto.nomeRestaurante;
            listItem.append(` VOTOS: ${voto.numVotos}`
            );
            lR.append(listItem);
        }
    }
}

definirEstadoVotacao = async () => {
    const response = await fetch('http://localhost:8080/votos/estado');
    const myJson = await response.json();

    if (myJson === true) {
        abrirVotacao();
    } else {
        encerrarVotacao();
    }
}

function encerrarVotacao() {
    $("#divVotacao").addClass('DISABLED');
    $("#textoVotacao").text('Votação encerrada. Abre às 18 horas da tarde.')
    buscarVotos();
}

function abrirVotacao() {
    $("#divVotacao").removeClass('DISABLED');
    $("#textoVotacao").text('Votação aberta. Encerra às 11 horas da manhã.')
    $("#textoVazio").addClass('HIDDEN');
    $('#listaResult').addClass('HIDDEN');
}