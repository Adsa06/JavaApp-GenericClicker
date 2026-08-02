package io.github.adsa06.presentation.ui.screens;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

import io.github.adsa06.domain.model.Upgrade;
import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.UpgradeViewModel;
import io.github.adsa06.utilities.Utilities;

public class UpgradesScreen {
    private Panel panel;
    private TranslationManager translationManager;
    private UpgradeViewModel upgradeViewModel;
    private int index = 0;

    public UpgradesScreen(
            UpgradeViewModel upgradeViewModel, TranslationManager translationManager) {
        this.upgradeViewModel = upgradeViewModel;
        this.translationManager = translationManager;

        initialize();
    }

    public Panel getPanel() {
        return panel;
    }

    private void initialize() {
        panel = new Panel();

        Panel root = new Panel(new LinearLayout(Direction.VERTICAL));

        Panel upgradesPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        List<Panel> upgradesPanels = new ArrayList<>();
        List<Upgrade> upgrades = new ArrayList<>(upgradeViewModel.getUpgrades());

        for (Upgrade upgrade : upgrades) {
            Panel upgradePanel = new Panel(new LinearLayout(Direction.VERTICAL));

            Label title = new Label(translationManager.getString(upgrade.getTitleId()));
            Label descripcion = new Label(translationManager.getString(upgrade.getDescripcionId()));
            Label cost = new Label(translationManager.getString("cost", Utilities.formatNum(upgrade.getCost())));
            Label isPurchased = new Label(translationManager.getString("purchased"));
            isPurchased.setVisible(upgrade.isPurchased());

            Button buy = new Button(translationManager.getString("buyUpgrades"));

            Button.Listener buyUpgrade = new Button.Listener() {

                @Override
                public void onTriggered(Button button) {
                    if (upgradeViewModel.buyUpgrade(upgrade)) {
                        isPurchased.setVisible(true);
                        button.setVisible(false);
                        button.setEnabled(false);
                    }
                }

            };
            buy.addListener(buyUpgrade);
            buy.setVisible(!upgrade.isPurchased());
            buy.setEnabled(!upgrade.isPurchased());
            upgradePanel.addComponent(title);
            upgradePanel.addComponent(descripcion);
            upgradePanel.addComponent(cost);
            upgradePanel.addComponent(isPurchased);
            upgradePanel.addComponent(buy);
            upgradesPanels.add(upgradePanel);

            translationManager.addListener(() -> {
                title.setText(translationManager.getString(upgrade.getTitleId()));
                descripcion.setText(translationManager.getString(upgrade.getDescripcionId()));
                cost.setText(translationManager.getString("cost", Utilities.formatNum(upgrade.getCost())));
                isPurchased.setText(translationManager.getString("purchased"));
                buy.setLabel(translationManager.getString("buyUpgrades"));
            });
        }

        upgradesPanel.addComponent(upgradesPanels.get(index));
        upgradesPanel.addComponent(upgradesPanels.get(index + 1));

        Button up = new Button("\u25B2", () -> {
            upgradesPanel.removeAllComponents();
            if ((index - 2) >= 0) {
                index -= 2;
                upgradesPanel.addComponent(upgradesPanels.get(index));
                if ((index + 1) < upgradesPanels.size())
                    upgradesPanel.addComponent(upgradesPanels.get(index + 1));

            } else {
                index = upgradesPanels.size() - 1;
                index -= (index % 2 == 0) ? 0 : 1;
                upgradesPanel.addComponent(upgradesPanels.get(index));
                if ((index + 1) < upgradesPanels.size())
                    upgradesPanel.addComponent(upgradesPanels.get(index + 1));

            }
        });
        Button down = new Button("\u25BC", () -> {
            upgradesPanel.removeAllComponents();
            if ((index + 2) < upgradesPanels.size()) {
                index += 2;
                upgradesPanel.addComponent(upgradesPanels.get(index));
                if ((index + 1) < upgradesPanels.size())
                    upgradesPanel.addComponent(upgradesPanels.get(index + 1));

            } else {
                index = 0;
                upgradesPanel.addComponent(upgradesPanels.get(index));
                if ((index + 1) < upgradesPanels.size())
                    upgradesPanel.addComponent(upgradesPanels.get(index + 1));

            }
        });

        root.addComponent(up);
        root.addComponent(upgradesPanel);
        root.addComponent(down);

        panel.addComponent(root);
    }
}