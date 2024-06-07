export interface IPickListData {
  value: number;
  description: string;
}

export interface IPickList {
  tableStatusList: IPickListData[];
  catetoryStatusList: IPickListData[];
  productStatusList: IPickListData[];
}
