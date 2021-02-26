package millenniumfinance.backend.data.v1.structures;

import millenniumfinance.backend.data.v1.classes.DataRowContainer;

import java.util.List;

public final class DataTable {
    private final List<DataRowContainer> dataRows;

    private DataTable(DataTableBuilder builder) {
        this.dataRows = builder.dataRows;
    }

    public String toJavaString() {
        return "DataTable{" +
                "dataRows=" + dataRows +
                '}';
    }

    @Override
    public String toString() {
        return this.getDataRows().toString()
                .replace("DataTable", "")
                .replaceAll("=", ": ");
    }

    public List<DataRowContainer> getDataRows() {
        return dataRows;
    }

    public static class DataTableBuilder {
        private List<DataRowContainer> dataRows;

        public DataTableBuilder dataRows(List<DataRowContainer> dataRows) {
            this.dataRows = dataRows;
            return this;
        }

        public DataTable build() {
            return new DataTable(this);
        }
    }
}
