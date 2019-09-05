export class BaseDto {
  uuid = ''
  tsCreation = ''
  tsUpdate = ''

  static getTsUpdateAsNumber(dto) {
    return Date.parse(dto.tsUpdate)
  }
  static getTsCreationAsDate(dto) {
    return new Date(Date.parse(dto.tsCreation))
  }
  static getTsUpdateAsDate(dto) {
    return new Date(Date.parse(dto.tsUpdate))
  }
  static getTsCreationAsNumber(dto) {
    return Date.parse(dto.tsCreation)
  }
}
