package millenniumfinance.backend.data.structures;

import millenniumfinance.backend.data.classes.DataRowContainer;

import java.util.List;

public final class DataTable {
    private final List<DataRowContainer> dataRows;

    private DataTable(DataTableBuilder builder) {
        this.dataRows = builder.dataRows;
    }

    @Override
    public String toString() {
        return "DataTable{" +
                "dataRows=" + dataRows +
                '}';
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

    public List<DataRowContainer> getDataRows() {
        return dataRows;
    }
}
