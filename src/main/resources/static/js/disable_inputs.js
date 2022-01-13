/**
 * Author: Rojas Morales Jose Daniel
 * Deshabilitar los inputs de acuerdo a la seleccion del elemento select
 */

$(function () {
  disableInputsClientesForm()
  disableInputsPagosForm()
  showDivReciboHonorarios()
})

function disableInputsClientesForm () {
  $('#regimenFiscalId').change(function () {
    if ($(this).val() === '601') {
      $('#razonSocial').prop('disabled', false)
      $('#nombreCliente').prop('disabled', true)
      $('#apPaternoCliente').prop('disabled', true)
      $('#apMaternoCliente').prop('disabled', true)
      $('#nombreCliente').val('')
      $('#apPaternoCliente').val('')
      $('#apMaternoCliente').val('')
    } else {
      $('#nombreCliente').prop('disabled', false)
      $('#apPaternoCliente').prop('disabled', false)
      $('#apMaternoCliente').prop('disabled', false)
      $('#razonSocial').prop('disabled', true)
      $('#razonSocial').val('')
    }
  })
}

function disableInputsPagosForm () {
  $('#selectFomasPago').change(function () {
    if ($(this).val() !== '01') {
      $('#divBancos').show()
    } else {
      $('#divBancos').hide()
    }
  })
}

function showDivReciboHonorarios () {
  $('#selectTipoHonorarios').change(function () {
    if ($(this).val() === '1') {
      $('#divReciboHonorariosButtons').show()
    }else{
      $('#divReciboHonorariosButtons').hide()
    }
  })
}

