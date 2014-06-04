package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import config.ConfigManager;
import config.Settings;

public class SettingsWindow
{
    private final Display display;

    private Shell window;
    
    private boolean settingsIsUpdated;
    
    private Text widthText;
    
    private Text heightText;   

    public SettingsWindow(Display display)
    {
        this.display = display;

        initShell();
        initButtons();
        initSettings();
    }

    private void initShell()
    {
        window = new Shell(display, SWT.APPLICATION_MODAL | SWT.CLOSE | SWT.TITLE);
        window.setLayout(new FormLayout());
        window.setText("Properties");
        window.setSize(300, 200);
    }

    private void initButtons()
    {
        final int width = 90;
        final int height = 30;
        
        // Button "Cancel" 
        Button cancelButton = new Button(window, SWT.PUSH);
        cancelButton.setText("Cancel");

        FormData cancelData = new FormData(width, height);
        final int numerator = 98;
        cancelData.right = new FormAttachment(numerator);
        cancelData.bottom = new FormAttachment(numerator);
        cancelButton.setLayoutData(cancelData);

        cancelButton.addSelectionListener(new CancelSelectionListener());
        
        // Button "Ok"
        Button okButton = new Button(window, SWT.PUSH);
        okButton.setText("OK");
        
        FormData okData = new FormData(width, height);
        final int  leftOffset = -5;
        okData.right = new FormAttachment(cancelButton, leftOffset, SWT.LEFT);
        okData.bottom = new FormAttachment(cancelButton, 0, SWT.BOTTOM);
        okButton.setLayoutData(okData);
        
        okButton.addSelectionListener(new OkSelectionListener());
    }

    private void initSettings()
    {   
        final int labelWidth = 80;
        final int labelHeight = 20;
        
        // Label "Field width" 
        Label widthLabel = new Label(window, SWT.LEFT);
        widthLabel.setText("Field width");       
        {
            FormData widthData = new FormData(labelWidth, labelHeight);
            final int numerator = 5;
            widthData.left = new FormAttachment(numerator);
            widthData.top = new FormAttachment(numerator);
            widthLabel.setLayoutData(widthData);
        }
        
               
        // Label "Field height"
        Label heightLabel = new Label(window, SWT.LEFT);
        heightLabel.setText("Field height");
        {
            FormData heightData = new FormData(labelWidth, labelHeight);
            final int bottomOffset = 5;
            heightData.left = new FormAttachment(widthLabel, 0, SWT.LEFT);
            heightData.top = new FormAttachment(widthLabel, bottomOffset, SWT.BOTTOM);
            heightLabel.setLayoutData(heightData);
        }
        
        Settings settings = ConfigManager.getSettings();
        final int rightOffset = 5;
        final int textWidth = 30;
        final int textHeight = 20;
        
        // Text "Field width" 
        widthText = new Text(window, SWT.CENTER | SWT.SINGLE);
        widthText.setText(Integer.toString(settings.getFieldWidth()));
        {
            FormData widthData = new FormData(textWidth, textHeight);
            widthData.left = new FormAttachment(widthLabel, rightOffset, SWT.RIGHT);
            widthData.top = new FormAttachment(widthLabel, 0, SWT.TOP);
            widthText.setLayoutData(widthData);
        }
        
        // Text "Field height" 
        heightText = new Text(window, SWT.CENTER | SWT.SINGLE);
        heightText.setText(Integer.toString(settings.getFieldHeight()));
        {
            FormData heightData = new FormData(textWidth, textHeight);
            heightData.left = new FormAttachment(heightLabel, rightOffset, SWT.RIGHT);
            heightData.top = new FormAttachment(heightLabel, 0, SWT.TOP);
            heightText.setLayoutData(heightData);
        } 
    }

    /**
     * Show the settings window
     * 
     * @param location - location on desktop
     * 
     * @return boolean flag settingsIsUpdated
     */
    public boolean showWindow(Point location)
    {
        window.setLocation(location);

        window.open();
        while (!window.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }

        return settingsIsUpdated;
    }

    private class CancelSelectionListener implements SelectionListener
    {
        public void widgetSelected(SelectionEvent event)
        {
            window.close();
        }

        public void widgetDefaultSelected(SelectionEvent event)
        {
            // not supported
        }
    }

    private class OkSelectionListener implements SelectionListener
    {
        public void widgetSelected(SelectionEvent event)
        {
            Settings settings = ConfigManager.getSettings();
            settings.setFieldWidth(Integer.parseInt(widthText.getText()));
            settings.setFieldHeight(Integer.parseInt(heightText.getText()));
            settingsIsUpdated = true;
            window.close();
        }

        public void widgetDefaultSelected(SelectionEvent event)
        {
            // not supported
        }
    }
}
