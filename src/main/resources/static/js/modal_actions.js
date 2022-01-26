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
      var uri = 'http://localhost:8090/operaciones/periodos/' + clienteRFC;
      var periodo;
      var detalle_status_pago;
      var class_status_pago;
      $.getJSON(uri, function (result) {
        if (result.length > 0) {
          $.each(result, function (key, value) {
            periodo = monthNames[value.mes - 1] + ' ' + value.ejercicio
            //validar el status del pago & asignar la clase del badge
            if(value.statusPago === 'A'){
              detalle_status_pago = 'Activo'
              class_status_pago = 'class="badge bg-info"'
            }else{
              detalle_status_pago = 'Vencido'
              class_status_pago = 'class="badge bg-danger"'
            }
            var contenedor = 
            '<div id="' + value.id + '" class="border border-secondary rounded mb-2 card-body bg-light">' +
                '<div class="media d-flex">'+
                  '<div class="align-self-center">'+
                    '<h4 class="card-title">' + periodo + '</h4>' +
                  '</div>'+
                  '<div class="media-body text-right">'+
                    '<span '+class_status_pago+'>'+detalle_status_pago+'</span></hr>' +
                    '<h6 class="mt-1">Fecha corte: '+ value.fechaCorte+'</h6>'+
                  '</div>'+
                '</div>'
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
      alert("RFC Vacio");
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
    var mes = $('#'+periodo).find('.card-title').html();
    //settear valores a los inputs
    $('#inputPeriodo').val(periodo)
    $('#inputConceptoMes').val(mes)
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
