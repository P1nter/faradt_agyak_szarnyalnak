public class DisappearingYarnTekton extends Tekton {
    // Your diagram showed "disappearing: boolean" - if this needs to change state,
    // you'd add a field and getter/setter, and isDisappearing() would return that field.
    // For now, assuming it's a fixed property of the type.
    public DisappearingYarnTekton(int id) { super(id); }
    public DisappearingYarnTekton() { super(); }
    @Override public boolean isDisappearing() { return true; }
}