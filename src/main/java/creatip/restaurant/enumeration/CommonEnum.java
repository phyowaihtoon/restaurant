package creatip.restaurant.enumeration;

public class CommonEnum {

    public enum TableStatus {
        AVAILABLE((short) 0, "Available"),
        RESERVED((short) 1, "Reserved"),
        OCCUPIED((short) 2, "Occupied");

        public short value;
        public String description;

        TableStatus(short value, String description) {
            this.value = value;
            this.description = description;
        }

        public static boolean isValid(byte inputValue) {
            for (TableStatus requestFrom : TableStatus.values()) {
                if (requestFrom.value == inputValue) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum CategoryStatus {
        ACTIVE((short) 0, "Active"),
        INACTIVE((short) 1, "Inactive");

        public short value;
        public String description;

        CategoryStatus(short value, String description) {
            this.value = value;
            this.description = description;
        }

        public static boolean isValid(byte inputValue) {
            for (CategoryStatus status : CategoryStatus.values()) {
                if (status.value == inputValue) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum ProductStatus {
        ACTIVE((short) 0, "Active"),
        INACTIVE((short) 1, "Inactive");

        public short value;
        public String description;

        ProductStatus(short value, String description) {
            this.value = value;
            this.description = description;
        }

        public static boolean isValid(byte inputValue) {
            for (ProductStatus status : ProductStatus.values()) {
                if (status.value == inputValue) {
                    return true;
                }
            }
            return false;
        }
    }
}
