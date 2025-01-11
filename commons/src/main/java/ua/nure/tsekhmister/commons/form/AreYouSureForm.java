package ua.nure.tsekhmister.commons.form;

public class AreYouSureForm {
    private boolean sure;

    public AreYouSureForm() {
    }

    public AreYouSureForm(boolean sure) {
        this.sure = sure;
    }

    public boolean isSure() {
        return sure;
    }

    public void setSure(boolean sure) {
        this.sure = sure;
    }
}
