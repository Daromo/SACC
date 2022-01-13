/**
 * Author: Rojas Morales Jose Daniel
 * Acciones para controlar los enventos de los modales
 */

const monthNames = [
  'Enero',
  'Febrero',
  'Marzo',
  'Abril',
  'Mayo',
  'Junio',
  'Julio',
  'Agosto',
  'Septiembre',
  'Octubre',
  'Noviembre',
  'Diciembre'
]

$(function () {
  $('#clientesModal').click(function () {
    listenerClicModalClientes()
  })

  $('#inputPeriodo').click(function () {
    var clienteRFC = $('#inputClienteRFC').val().replace(/ /g, '')
    if (clienteRFC != '') {
      clearModalPeriodos()
      var uri = 'http://localhost:8090/operaciones/periodos/' + clienteRFC
      var periodo

      $.getJSON(uri, function (result) {
        if (result.length > 0) {
          $.each(result, function (key, value) {
            console.log(typeof result)
            periodo = monthNames[value.mes - 1] + ' ' + value.ejercicio
            var contenedor =
              '<div id="' +
              value.id +
              '" class="border border-secondary rounded mb-2 card-body bg-light">' +
              '<h5 class="card-title">' +
              periodo +
              '<h5>' +
              '</div>'
            $('#items_periodos').append(contenedor)
          })
        } else {
          $('#items_periodos').append(
            '<h5 class="card-title">Sin resultados para el cliente: ' +
              clienteRFC +'</h5> <p>Para más información consulte al Administrador del sistema SACC</p>'
          )
        }
      })

      var myModal = new bootstrap.Modal(
        document.getElementById('modalPeriodos'),
        {}
      )
      myModal.show()
      listenerClicModalPeriodos()
    } else {
      console.log('RFC vacio')
    }
  })
})

function clearModalPeriodos () {
  $('#inputPeriodo').val('')
  $('#modalPeriodos').on('hidden.bs.modal', function (e) {
    $('#items_periodos').html('')
  })
}

function listenerClicModalPeriodos () {
  $('body').on('click', '#items_periodos div', function (e) {
    var periodo = $(this).attr('id')
    console.log(periodo)
    //settear valores a los inputs
    $('#inputPeriodo').val(periodo)
    //close modal
    $('#modalPeriodos').modal('toggle')
  })
}

function listenerClicModalClientes () {
  $('body').on('click', '#items_clientes div', function (e) {
    var rfc = $(this).attr('id')
    var nombre = $('#'+rfc).find('.card-title').html();
    console.log(rfc)
    //settear valores a los inputs
    $('#inputClienteRFC').val(rfc)
    $('#nombre').val(nombre);
    //close modal
    $('#clientesModal').modal('toggle')
  })
}
