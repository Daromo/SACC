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
    if ($(this).val() === '03') {
      $('#divListBancos').show()
      $('#divFolioCheque').hide(1000)
      $('#inputFolioCheque').val('')
    } else if ($(this).val() === '02'){
      $('#divFolioCheque').show()
      $('#divListBancos').hide(1000)
      $('#selectBancos').val('999')
    }else{
      $('#divListBancos').hide(1000)
      $('#divFolioCheque').hide(1000)
      $('#selectBancos').val('999')
      $('#inputFolioCheque').val('')
    }
  })

  $('#')
}

function showDivReciboHonorarios () {
  $('#selectTipoHonorarios').change(function () {
    if ($(this).val() === '1') {
      $('#divReciboHonorariosButtons').show()
    }else{
      $('#divReciboHonorariosButtons').hide(1000)
    }
  })
}


